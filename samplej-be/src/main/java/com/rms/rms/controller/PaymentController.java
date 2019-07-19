package com.rms.rms.controller;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.config.properties.ApplicationProperties;
import com.rms.rms.common.dto.PaymentDto;
import com.rms.rms.common.dto.PersonDto;
import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.dto.UserDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.exception.InvalidViewModelException;
import com.rms.rms.common.view_model.PaymentCreateModel;
import com.rms.rms.common.view_model.PaymentSearchCriteria;
import com.rms.rms.common.view_model.PaymentUpdateModel;
import com.rms.rms.service.*;
import com.rms.rms.service.model.Payment;
import com.rms.rms.task_processor.TaskProcessorCreateEmailAndNotification;
import com.rms.rms.task_processor.TaskProcessorHandleBigReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.concurrent.ExecutorService;

/**
 * homertruong
 */

public class PaymentController {

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private TaskProcessorCreateEmailAndNotification tpCreateEmailAndNotification;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private SubsEmailTemplateService subsEmailTemplateService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private AuthenService authenService;

    @Autowired
    private PersonService personService;

    @Autowired
    @Qualifier("taskExecutorService")
    private ExecutorService taskExecutorService;

    @Autowired
    private TaskProcessorHandleBigReport tpHandleBigReport;

    public ResponseDto approve (String id) throws BusinessException {
        // approve Payment
        PaymentDto dto = paymentService.approve(id);
        UserDto loggedUserDto = authenService.getLoggedUser();

        // create Email and Notification for Affiliate in background mode
        taskExecutorService.submit(() -> {
            tpCreateEmailAndNotification.processOnPaymentApproved(dto, loggedUserDto);
        });

        return new ResponseDto(dto);
    }

    public ResponseDto create (PaymentCreateModel createModel) throws BusinessException {
        PaymentDto dto = paymentService.create(createModel);
        UserDto loggedUserDto = authenService.getLoggedUser();

        taskExecutorService.submit(() -> {
            tpCreateEmailAndNotification.processOnPaymentCreated(dto, loggedUserDto);
        });

        return new ResponseDto(dto);
    }

    public ResponseEntity<byte[]> export(Date from, Date to, HttpServletResponse response) throws BusinessException {
        if(from == null || to == null) {
            throw new InvalidViewModelException("'from' or 'to' can not be null !");
        }

        if (from.after(to)) {
            throw new InvalidViewModelException("'from' must <= 'to' !");
        }


        // export Payments to byte array
        byte[] contents = paymentService.export(from, to);
        String filename = "payments-" + from.toString() + "-to-" + to.toString() + ".xlsx";

        // handle if report is big
        if( contents.length > applicationProperties.getMaxFileSize() ) {
            PersonDto personDto = personService.getByEmail(authenService.getLoggedUser().getEmail());
            Runnable task = () -> tpHandleBigReport.process(contents, filename, personDto, Payment.class.getSimpleName());
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

    public ResponseDto get (String id) throws BusinessException {
        PaymentDto dto = paymentService.get(id);
        return new ResponseDto(dto);
    }

    public ResponseDto reject (String id, String reason) throws BusinessException {
        // reject Payment
        PaymentDto dto = paymentService.reject(id, reason);

        // create Notification for Affiliate in background mode
        UserDto loggedUserDto = authenService.getLoggedUser();
        taskExecutorService.submit(() -> {
            tpCreateEmailAndNotification.processOnPaymentRejected(dto, loggedUserDto);
        });

        return new ResponseDto(dto);
    }

    public ResponseDto search (SearchCriteria<PaymentSearchCriteria> vmSearchCriteria) throws BusinessException {
        SearchResult<PaymentDto> dtoSearchResult = paymentService.search(vmSearchCriteria);
        return new ResponseDto(dtoSearchResult);
    }

    public ResponseDto update (String id, PaymentUpdateModel updateModel) throws BusinessException {
        PaymentDto paymentDto = paymentService.update(id, updateModel);
        return new ResponseDto(paymentDto);
    }
}
