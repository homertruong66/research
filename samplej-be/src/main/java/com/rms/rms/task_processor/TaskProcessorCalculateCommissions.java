package com.rms.rms.task_processor;

import com.rms.rms.common.dto.OrderDto;
import com.rms.rms.common.dto.OrderLineDto;
import com.rms.rms.common.dto.SubsEmailTemplateDto;
import com.rms.rms.common.dto.UserDto;
import com.rms.rms.common.util.MyEmailUtil;
import com.rms.rms.common.util.MyJsonUtil;
import com.rms.rms.common.view_model.NotificationCreateModel;
import com.rms.rms.common.view_model.TaskCreateModel;
import com.rms.rms.service.*;
import com.rms.rms.service.model.Email;
import com.rms.rms.service.model.Order;
import com.rms.rms.service.model.SubsEmailTemplate;
import com.rms.rms.service.model.Task;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class TaskProcessorCalculateCommissions {

    private Logger logger = Logger.getLogger(TaskProcessorCalculateCommissions.class);

    @Autowired
    private CommissionService commissionService;

    @Autowired
    private SubsEmailTemplateService subsEmailTemplateService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private TaskService taskService;

    public void process(String orderId, UserDto loggedUserDto) {
        logger.info("process: " + orderId);

        // put Authentication object to Spring Security Context
        Authentication authentication = TaskProcessorUtil.createAuthentication(loggedUserDto);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // process task
        NotificationCreateModel notificationCreateModel = new NotificationCreateModel();
        try {
            // calculate Commissions
            OrderDto orderDto = commissionService.calculateCommissions(orderId);

            // create a Notification for Seller
            notificationCreateModel.setMessage("Your Order '" + orderDto.getNumber() + "' has just been approved.");
            notificationCreateModel.setToUserId(orderDto.getAffiliate().getId());
            notificationCreateModel.setType(Order.class.getSimpleName());
            notificationCreateModel.setTargetId(orderDto.getId());
            notificationService.create(notificationCreateModel);

            // send email to Seller if any
            if (orderDto.getAffiliate() != null) {
                NumberFormat format = NumberFormat.getCurrencyInstance();
                String commission = format.format(commissionService.calculateEarningByAffiliateIdAndOrderId(orderDto.getAffiliate().getId(),orderDto.getId()));
                commission = commission.replace("$","");

                SubsEmailTemplateDto subsEmailTemplate = subsEmailTemplateService.getByType(SubsEmailTemplate.TYPE_AFF_ORDER_APPROVED);
                String content = subsEmailTemplate.getContent();
                content = content.replace("[full_name]", String.join(" ", orderDto.getAffiliate().getLastName(), orderDto.getAffiliate().getFirstName()));
                content = content.replace("[order_id]", orderDto.getNumber());
                content = content.replace("[commission]", commission + " VND");
                content = content.replace("[customer_name]", orderDto.getCustomer().getFullname());
                content = content.replace("[customer_email]", orderDto.getCustomer().getEmail());
                List<String> productNames = new ArrayList<>();
                for (OrderLineDto orderLineDto : orderDto.getOrderLines()) {
                    productNames.add(orderLineDto.getProduct().getName());
                }
                content = content.replace("[product]", productNames.toString());
                subsEmailTemplate.setContent(content);

                Email email = MyEmailUtil.createEmailFromSubsEmailTemplate(
                    subsEmailTemplate, orderDto.getAffiliate().getUser().getEmail(),
                    String.join(" ", orderDto.getAffiliate().getLastName(), orderDto.getAffiliate().getFirstName())
                );
                emailService.send(email);
            }
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            List<NotificationCreateModel> failedNotifications = new ArrayList<>();
            failedNotifications.add(notificationCreateModel);
            createTask(Task.ACTION_CREATE_NOTIFICATIONS, Task.STATUS_NEW, MyJsonUtil.gson.toJson(failedNotifications));
        }
    }


    // Utilities
    private void createTask(String action, String status, String params) {
        TaskCreateModel createModel = new TaskCreateModel();
        createModel.setAction(action);
        createModel.setStatus(status);
        createModel.setParams(params);
        taskService.create(createModel);
    }
}
