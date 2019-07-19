package com.rms.rms.controller;

import com.rms.rms.common.dto.DashboardDto;
import com.rms.rms.common.dto.PersonDto;
import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.AwsS3PreSignedUrlModel;
import com.rms.rms.common.view_model.ChangePasswordModel;
import com.rms.rms.common.view_model.ResetPasswordModel;
import com.rms.rms.common.view_model.SmsModel;
import com.rms.rms.service.*;
import com.rms.rms.task_processor.TaskProcessorCreateEmailAndNotification;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

/**
 * homertruong
 */
public class GeneralController {

    private Logger logger = Logger.getLogger(GeneralController.class);

    @Autowired
    private TaskProcessorCreateEmailAndNotification tpCreateEmailAndNotification;

    @Autowired
    private AwsService awsService;

    @Autowired
    private AuthenService authenService;

    @Autowired
    private SmsService smsService;

    @Autowired
    private PersonService personService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ShareService shareService;

    @Autowired
    private HealthCheckService healthCheckService;

    @Autowired
    @Qualifier("taskExecutorService")
    private ExecutorService taskExecutorService;

    public ResponseDto changePassword(ChangePasswordModel model) throws BusinessException {
        PersonDto personDto = personService.changePassword(model);
        return new ResponseDto(personDto);
    }

    public ResponseDto generateAwsS3PreSignedUrl (AwsS3PreSignedUrlModel model) throws BusinessException {
        return new ResponseDto(awsService.generateAwsS3PreSignedUrl(model));
    }

    public void healthCheck () {
        healthCheckService.check();
    }

    public void resetPassword(ResetPasswordModel model) throws BusinessException, IOException{
        taskExecutorService.submit(() -> {
            tpCreateEmailAndNotification.processOnResetPassword(model);
        });
    }

    public ResponseDto sendSms (SmsModel smsModel) throws BusinessException {
        return new ResponseDto(smsService.send(smsModel));
    }

    public ResponseDto viewDashboard() throws BusinessException {
        DashboardDto dashboardDto = new DashboardDto();
        dashboardDto.setAffiliateCount(personService.countAffiliates());
        dashboardDto.setCustomerCount(customerService.count());
        dashboardDto.setOrderCount(orderService.count());
        dashboardDto.setClicksByDate(shareService.getClicksByDate());

        // Dashboard Order revenue
        Double orderRevenue = orderService.getRevenue(null);
        dashboardDto.setOrderRevenue(orderRevenue);

        // Dashboard Approved Order revenue = approvedOrderRevenue + commissionsDoneOrderRevenue
        Double approvedOrderRevenue = orderService.getApprovedRevenue();
        Double commissionsDoneOrderRevenue = orderService.getCommissionsDoneRevenue();
        dashboardDto.setApprovedOrderRevenue(approvedOrderRevenue + commissionsDoneOrderRevenue);

        // Dashboard NonApproved Order revenue = Dashboard Order revenue - Dashboard Approved Order revenue
        Double nonApprovedOrderRevenue = dashboardDto.getOrderRevenue() - dashboardDto.getApprovedOrderRevenue();
        dashboardDto.setNonApprovedOrderRevenue(nonApprovedOrderRevenue);

        return new ResponseDto(dashboardDto);
    }
}
