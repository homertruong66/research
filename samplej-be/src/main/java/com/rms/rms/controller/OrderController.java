package com.rms.rms.controller;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.config.properties.ApplicationProperties;
import com.rms.rms.common.dto.*;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.exception.InvalidViewModelException;
import com.rms.rms.common.view_model.OrderCreateModel;
import com.rms.rms.common.view_model.OrderSearchCriteria;
import com.rms.rms.common.view_model.OrderUpdateModel;
import com.rms.rms.service.*;
import com.rms.rms.service.model.Order;
import com.rms.rms.task_processor.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutorService;

/**
 * homertruong
 */
public class OrderController {

    private Logger logger = Logger.getLogger(OrderController.class);

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private AuthenService authenService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TaskProcessorHandleBigReport tpHandleBigReport;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PackageConfigAppliedService packageConfigAppliedService;

    @Autowired
    private PersonService personService;

    @Autowired
    private SubsEmailTemplateService subsEmailTemplateService;

    @Autowired
    private SubsGetflyConfigService subsGetflyConfigService;

    @Autowired
    private SubsGetResponseConfigService subsGetResponseConfigService;

    @Autowired
    private SubsInfusionConfigService subsInfusionConfigService;

    @Autowired
    @Qualifier("taskExecutorService")
    private ExecutorService taskExecutorService;

    @Autowired
    private TaskProcessorCalculateCommissions tpCalculateCommissions;

    @Autowired
    private TaskProcessorCreateEmailAndNotification tpCreateEmailAndNotification;

    @Autowired
    private TaskProcessorPushDataToGetfly tpPushDataToGetfly;

    @Autowired
    private TaskProcessorPushDataToGetResponse tpPushDataToGetResponse;

    @Autowired
    private TaskProcessorPushDataToInfusion tpPushDataToInfusion;

    public ResponseDto approve(String id) throws BusinessException {
        // approve Order
        OrderDto dto = orderService.approve(id);

        // calculate Commissions on Order approved in background mode
        UserDto loggedUserDto = authenService.getLoggedUser();
        Runnable task = () -> tpCalculateCommissions.process(id, loggedUserDto);
        taskExecutorService.submit(task);

        return new ResponseDto(dto);
    }

    public ResponseDto create(OrderCreateModel createModel) throws BusinessException {
        // create Order
        OrderDto dto = orderService.create(createModel);

        // process other stuffs in background mode
        UserDto loggedUserDto = authenService.getLoggedUser();
        String subscriberId = dto.getChannel().getSubscriberId();
        PackageConfigAppliedDto pca = packageConfigAppliedService.get(subscriberId);

          // push data to Infusion in background mode if any
        if (pca.getHasInfusion()) {
            SubsInfusionConfigDto subsInfusionConfigDto = subsInfusionConfigService.get(subscriberId);
            Runnable task = () -> tpPushDataToInfusion.process(dto, subsInfusionConfigDto, loggedUserDto);
            taskExecutorService.submit(task);
        }

          // push data to Getfly in background mode if any
        if (pca.getHasGetfly()) {
            SubsGetflyConfigDto subsGetflyConfigDto = subsGetflyConfigService.get(subscriberId);
            Runnable task = () -> tpPushDataToGetfly.process(dto, subsGetflyConfigDto, loggedUserDto);
            taskExecutorService.submit(task);
        }

        // push data to GetResponse in background mode if any
        if (pca.getHasGetResponse()) {
            SubsGetResponseConfigDto subsGetResponseConfigDto = subsGetResponseConfigService.get(subscriberId);
            Runnable task = () -> tpPushDataToGetResponse.process(dto, subsGetResponseConfigDto, loggedUserDto);
            taskExecutorService.submit(task);
        }

          // process Email and Notification in background mode
            Runnable task = () -> tpCreateEmailAndNotification.processOnOrderCreated(dto, loggedUserDto);
            taskExecutorService.submit(task);

        return new ResponseDto(dto);
    }

    public ResponseEntity<byte[]> export(Date from, Date to, HttpServletResponse response) throws BusinessException {
        if(from == null || to == null) {
            throw new InvalidViewModelException("'from' or 'to' can not be null !");
        }

        if (from.after(to)) {
            throw new InvalidViewModelException("'from' must <= 'to' !");
        }

        // export Orders to byte array
        byte[] contents = orderService.export(from, to);
        String filename = "orders-" + from.toString() + "-to-" + to.toString() + ".xlsx";

        // handle if report is big
        if( contents.length > applicationProperties.getMaxFileSize() ) {
            PersonDto personDto = personService.getByEmail(authenService.getLoggedUser().getEmail());
            Runnable task = () -> tpHandleBigReport.process(contents, filename, personDto, Order.class.getSimpleName());
            taskExecutorService.submit(task);

            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(contents, headers, HttpStatus.OK);

        return responseEntity;
    }

    public ResponseDto get(String id) throws BusinessException {
        OrderDto dto = orderService.get(id);
        return new ResponseDto(dto);
    }

    public ResponseDto reject(String id, String reason) throws BusinessException {
        OrderDto dto = orderService.reject(id, reason);
        UserDto loggedUserDto = authenService.getLoggedUser();

        // create notification
        if( dto.getAffiliate() != null ) {
            taskExecutorService.submit(() -> {
                tpCreateEmailAndNotification.processOnOrderRejected(dto, loggedUserDto);
            });
        }

        return new ResponseDto(dto);
    }

    public ResponseDto resendGetfly(String id) throws BusinessException, IOException {
        OrderDto orderDto = orderService.get(id);
        OrderDto dto = orderService.sendDataToGetfly(orderDto);
        return new ResponseDto(dto);
    }

    public ResponseDto resendInfusion(String id) throws BusinessException, IOException {
        OrderDto orderDto = orderService.get(id);
        OrderDto dto = orderService.sendDataToInfusion(orderDto);
        return new ResponseDto(dto);
    }

    public ResponseDto search(SearchCriteria<OrderSearchCriteria> vmSearchCriteria) throws BusinessException {
        SearchResult<OrderDto> dtoSearchResult = orderService.search(vmSearchCriteria);
        return new ResponseDto(dtoSearchResult);
    }

    public ResponseDto update(String id, OrderUpdateModel updateModel) throws BusinessException {
        OrderDto dto = orderService.update(id, updateModel);
        return new ResponseDto(dto);
    }

    public ResponseDto view(String id)  throws BusinessException {
        OrderViewDto dto = orderService.view(id);
        return new ResponseDto(dto);
    }
}
