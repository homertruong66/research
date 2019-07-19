package com.rms.rms.controller;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.config.properties.CustomerSupportEmailProperties;
import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.dto.SubscriberDto;
import com.rms.rms.common.dto.SubscriberViewDto;
import com.rms.rms.common.dto.UserDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.SubscriberCreateModel;
import com.rms.rms.common.view_model.SubscriberExtendModel;
import com.rms.rms.common.view_model.SubscriberSearchCriteria;
import com.rms.rms.common.view_model.SubscriberUpdateModel;
import com.rms.rms.service.*;
import com.rms.rms.task_processor.TaskProcessorCreateEmailAndNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

/**
 * homertruong
 */
public class SubscriberController {

    @Autowired
    private TaskProcessorCreateEmailAndNotification tpCreateEmailAndNotification;

    @Autowired
    private SubscriberService subscriberService;

    @Autowired
    private PersonService personService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ShareService shareService;

    @Autowired
    private AuthenService authenService;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CustomerSupportEmailProperties customerSupportEmailProperties;

    @Autowired
    private PackageConfigAppliedService packageConfigAppliedService;

    @Autowired
    @Qualifier("taskExecutorService")
    private ExecutorService taskExecutorService;

    public ResponseDto create(SubscriberCreateModel createModel) throws BusinessException, IOException {
        SubscriberDto dto = subscriberService.create(createModel);

        if (createModel.getFirstChannelDomainName() != null) {
            UserDto loggedUserDto = authenService.getLoggedUser();

            taskExecutorService.submit(() -> {
                tpCreateEmailAndNotification.processOnSubscriberCreated(dto, createModel, loggedUserDto);
            });
        }

        return new ResponseDto(dto);
    }

    public ResponseDto extend(String id, SubscriberExtendModel extendModel) throws BusinessException {
        SubscriberDto dto = packageConfigAppliedService.extend(id, extendModel);

        return new ResponseDto(dto);
    }

    public ResponseDto get(String id) throws BusinessException {
        SubscriberDto dto = subscriberService.get(id);
        return new ResponseDto(dto);
    }

    public ResponseDto renew(String id) throws BusinessException, IOException{
        SubscriberDto dto = subscriberService.renew(id);

        UserDto loggedUserDto = authenService.getLoggedUser();

        taskExecutorService.submit(() -> {
            tpCreateEmailAndNotification.processOnSubscriberRenewed(dto, loggedUserDto);
        });

        return new ResponseDto(dto);
    }

    public ResponseDto resetData(String id) throws BusinessException {
        SubscriberDto dto = subscriberService.resetData(id);
        return new ResponseDto(dto);
    }

    public ResponseDto search(SearchCriteria<SubscriberSearchCriteria> vmSearchCriteria) throws BusinessException {
        SearchResult<SubscriberDto> dtoSearchResult = subscriberService.search(vmSearchCriteria);
        return new ResponseDto(dtoSearchResult);
    }

    public ResponseDto update(String id, SubscriberUpdateModel updateModel) throws BusinessException {
        SubscriberDto dto = subscriberService.update(id, updateModel);
        return new ResponseDto(dto);
    }

    public ResponseDto upgrade(String id, String packageType) throws BusinessException, IOException {
        SubscriberDto dto = subscriberService.upgrade(id, packageType);

        UserDto loggedUserDto = authenService.getLoggedUser();

        taskExecutorService.submit(() -> {
            tpCreateEmailAndNotification.processOnSubscriberUpgraded(dto, loggedUserDto);
        });

        return new ResponseDto(dto);
    }

    public ResponseDto upload(MultipartFile uploadedFile) throws BusinessException {
        subscriberService.upload(uploadedFile);

        return new ResponseDto("ok");
    }

    public ResponseDto view(String id)  throws BusinessException {
        SubscriberViewDto subscriberViewDto = new SubscriberViewDto();
        subscriberViewDto.setAffiliateCount(personService.countAffiliatesBySubscriberId(id));
        subscriberViewDto.setCustomerCount(customerService.countBySubscriberId(id));
        subscriberViewDto.setOrderCount(orderService.countBySubscriberId(id));
        subscriberViewDto.setClicksByDate(shareService.getClicksByDateBySubscriberId(id));

        // SubscriberView Order revenue
        Double orderRevenue = orderService.getRevenueBySubscriberId(id, null);
        subscriberViewDto.setOrderRevenue(orderRevenue);

        // SubscriberView Approved Order revenue = approvedOrderRevenue + commissionsDoneOrderRevenue
        Double approvedOrderRevenue = orderService.getApprovedRevenueBySubscriberId(id);
        Double commissionsDoneOrderRevenue = orderService.getCommissionsDoneRevenueBySubscriberId(id);
        subscriberViewDto.setApprovedOrderRevenue(approvedOrderRevenue + commissionsDoneOrderRevenue);

        // SubscriberView NonApproved Order revenue = SubscriberView Order revenue - SubscriberView Approved Order revenue
        Double nonApprovedOrderRevenue = subscriberViewDto.getOrderRevenue() - subscriberViewDto.getApprovedOrderRevenue();
        subscriberViewDto.setNonApprovedOrderRevenue(nonApprovedOrderRevenue);

        return new ResponseDto(subscriberViewDto);
    }

}
