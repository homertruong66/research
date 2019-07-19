package com.rms.rms.task_processor;

import com.rms.rms.common.config.properties.CustomerSupportEmailProperties;
import com.rms.rms.common.config.properties.FeedbackEmailProperties;
import com.rms.rms.common.dto.*;
import com.rms.rms.common.util.MyEmailUtil;
import com.rms.rms.common.util.MyJsonUtil;
import com.rms.rms.common.view_model.*;
import com.rms.rms.service.*;
import com.rms.rms.service.model.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskProcessorCreateEmailAndNotification {

    private Logger logger = Logger.getLogger(TaskProcessorCreateEmailAndNotification.class);

    @Autowired
    private FeedbackEmailProperties feedbackEmailProperties;

    @Autowired
    private PersonService personService;

    @Autowired
    private SubsEmailTemplateService subsEmailTemplateService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private SubscriberService subscriberService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private CustomerSupportEmailProperties customerSupportEmailProperties;


    private final String EVENT_TYPE_CREATED = "CREATED";
    private final String EVENT_TYPE_APPROVED = "APPROVED";
    private final String EVENT_TYPE_REJECTED = "REJECTED";

    public void processOnAffiliateCreated(AffiliateDto affiliateDto, UserDto loggedUserDto, AffiliateCreateModel affiliateCreateModel,
                                          boolean isNewAffiliate) {
        logger.info("processOnAffiliateCreated : " + affiliateDto + ", " + loggedUserDto + ", " + affiliateCreateModel + ", " + isNewAffiliate);

        // put Authentication object to Spring Security Context
        Authentication authentication = TaskProcessorUtil.createAuthentication(loggedUserDto);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        try {
            List<Email> emails = new ArrayList<>();

            // process email to newly created Affiliate asynchronously if any
            if (isNewAffiliate) {
                String name = affiliateDto.getLastName() + " " + affiliateDto.getFirstName();
                String title = emailService.getActivateEmailTitle();
                String channelUrl = affiliateCreateModel.getChannelUrl();
                channelUrl = channelUrl != null? "&channel_url=" + affiliateCreateModel.getChannelUrl() : "";

                String content = emailService.getActivateEmailContent();
                content = content.replace("[feUrl]",affiliateCreateModel.getFeUrl());
                content = content.replace("[activationCode]",affiliateDto.getActivationCode());
                content = content.replace("[channelUrl]", channelUrl);

                String emailTemplate = emailService.getSystemEmailTemplate();
                emailTemplate = emailTemplate.replace("[title]", title);
                emailTemplate = emailTemplate.replace("[content]", content);

                Email email = MyEmailUtil.createEmailFromEmailTemplate(title,emailTemplate,affiliateDto.getUser().getEmail(),name);
                emails.add(email);
            }

            // process email to referrer if present
            SubscriberDto subscriberDto = subscriberService.getByDomainName(affiliateCreateModel.getSubscriberDomainName());
            if (!StringUtils.isBlank(affiliateCreateModel.getReferrer())) {
                AffiliateDto referringAffiliate = personService.getAffiliateByNickname(affiliateCreateModel.getReferrer());
                String name = referringAffiliate.getLastName() + " " + referringAffiliate.getFirstName();
                String title = emailService.getCreateAffToReferrerEmailTitle();

                String content = emailService.getCreateAffToReferrerEmailContent();
                content = content.replace("[name]", affiliateDto.getLastName() + " " + affiliateDto.getFirstName());
                content = content.replace("[nickname]", affiliateDto.getNickname());
                content = content.replace("[company_name]", subscriberDto.getCompanyName());
                content = content.replace("[feUrl]",affiliateCreateModel.getFeUrl());

                String emailTemplate = emailService.getSystemEmailTemplate();
                emailTemplate = emailTemplate.replace("[title]", title);
                emailTemplate = emailTemplate.replace("[content]", content);

                Email email = MyEmailUtil.createEmailFromEmailTemplate(title,emailTemplate,referringAffiliate.getUser().getEmail(),name);
                emails.add(email);
            }

            // process emails to SubsAdmins of the selected Subscriber
            List<String> subsAdminEmails = personService.getSubsAdminEmailsBySubscriberId(subscriberDto.getId());
            for (String emailString: subsAdminEmails) {
                String name = "SubAdmin";
                String title = emailService.getCreateAffToSubsAdminEmailTitle();

                String content = emailService.getCreateAffToSubsAdminEmailContent();
                content = content.replace("[name]", affiliateDto.getLastName() + " " + affiliateDto.getFirstName());
                content = content.replace("[nickname]", affiliateDto.getNickname());
                content = content.replace("[feUrl]", affiliateCreateModel.getFeUrl());

                String emailTemplate = emailService.getSystemEmailTemplate();
                emailTemplate = emailTemplate.replace("[title]", title);
                emailTemplate = emailTemplate.replace("[content]", content);

                Email email = MyEmailUtil.createEmailFromEmailTemplate(title, emailTemplate, emailString, name);
                emails.add(email);
            }

            emailService.send(emails);
        }
        catch(Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
    public void processOnAffiliateConverted(AffiliateDto dto, UserDto loggedUserDto, OrderDto orderDto, String feUrl,
                                            boolean isNewAffiliate) {
        logger.info("processOnAffiliateConverted: " + dto + ", " + loggedUserDto + ", " + orderDto + ", " + feUrl
                + ", " + isNewAffiliate);

        // put Authentication object to Spring Security Context
        Authentication authentication = TaskProcessorUtil.createAuthentication(loggedUserDto);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        CustomerDto customerDto = orderDto.getCustomer();
        AffiliateDto affiliateDto = orderDto.getAffiliate();

        // process task
        try {
            // send email to newly created Affiliate asynchronously if any
            if (isNewAffiliate) {
                String title = emailService.getActivateEmailTitle();
                String affName = dto.getLastName() + " " + dto.getFirstName();
                String affEmail = dto.getUser().getEmail();

                String content = emailService.getActivateEmailContent();
                content = content.replace("[feUrl]", feUrl);
                content = content.replace("[activationCode]", dto.getActivationCode());
                content = content.replace("[channelUrl]", "");

                String emailTemplate = emailService.getSystemEmailTemplate();
                emailTemplate = emailTemplate.replace("[title]", title);
                emailTemplate = emailTemplate.replace("[content]", content);

                Email email = MyEmailUtil.createEmailFromEmailTemplate(title, emailTemplate, affEmail, affName);
                emailService.send(email);
            }

            // send email to referrer if present
            SubscriberDto subscriberDto = customerDto.getSubscriber();
            if (affiliateDto != null) {
                String title = emailService.getCreateAffToReferrerEmailTitle();
                String referrerName = affiliateDto.getLastName() + " " + affiliateDto.getFirstName();
                String referrerEmail = affiliateDto.getUser().getEmail();

                String content = emailService.getCreateAffToReferrerEmailContent();
                content = content.replace("[name]", dto.getLastName() + " " + dto.getFirstName());
                content = content.replace("[nickname]", dto.getNickname());
                content = content.replace("[company_name]", subscriberDto.getCompanyName());
                content = content.replace("[feUrl]", feUrl);

                String emailTemplate = emailService.getSystemEmailTemplate();
                emailTemplate = emailTemplate.replace("[title]", title);
                emailTemplate = emailTemplate.replace("[content]", content);

                Email email = MyEmailUtil.createEmailFromEmailTemplate(title, emailTemplate, referrerEmail, referrerName);
                emailService.send(email);
            }

            // send emails to SubsAdmins of the selected Subscriber
            List<Email> emails = new ArrayList<>();
            List<String> subsAdminEmails = personService.getSubsAdminEmailsBySubscriberId(subscriberDto.getId());
            for (String subsAdminEmail : subsAdminEmails) {
                String name = "SubAdmin";
                String title = emailService.getCreateAffToSubsAdminEmailTitle();

                String content = emailService.getCreateAffToSubsAdminEmailContent();
                content = content.replace("[name]", dto.getLastName() + " " + dto.getFirstName());
                content = content.replace("[nickname]", dto.getNickname());
                content = content.replace("[feUrl]", feUrl);

                String emailTemplate = emailService.getSystemEmailTemplate();
                emailTemplate = emailTemplate.replace("[title]", title);
                emailTemplate = emailTemplate.replace("[content]", content);

                Email email = MyEmailUtil.createEmailFromEmailTemplate(title, emailTemplate, subsAdminEmail, name);
                emails.add(email);
            }
            emailService.send(emails);
        }
        catch(Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    public void processOnAffiliateSignedUp(AffiliateCreateModel affiliateCreateModel, UserDto loggedUserDto) {
        logger.info("processOnAffiliateSignedUp : " + affiliateCreateModel + ", " + loggedUserDto);

        // put Authentication object to Spring Security Context
        Authentication authentication = TaskProcessorUtil.createAuthentication(loggedUserDto);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        try {
            SubscriberDto subscriberDto = subscriberService.getByDomainName(affiliateCreateModel.getSubscriberDomainName());

            // process send email to affiliate
            SubsEmailTemplateDto subsEmailTemplate = subsEmailTemplateService.getBySubscriberIdAndType(
                    SubsEmailTemplate.TYPE_AFF_SIGN_UP, subscriberDto.getId());
            String content = subsEmailTemplate.getContent();
            content = content.replace("[full_name]", String.join(" ", affiliateCreateModel.getLastName(),
                    affiliateCreateModel.getFirstName()));
            subsEmailTemplate.setContent(content);
            Email email = MyEmailUtil.createEmailFromSubsEmailTemplate(
                    subsEmailTemplate, affiliateCreateModel.getEmail(),
                    String.join(" ", affiliateCreateModel.getLastName(), affiliateCreateModel.getFirstName())
            );

            emailService.send(email);
        }
        catch(Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    public void processOnAffiliatesInvite(AffiliateInviteModel affiliateInviteModel, UserDto loggedUserDto) {
        logger.info("processOnAffiliatesInvite : " + affiliateInviteModel + ", " + loggedUserDto);

        // put Authentication object to Spring Security Context
        Authentication authentication = TaskProcessorUtil.createAuthentication(loggedUserDto);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        try {
            List<Email> emails = new ArrayList<>();
            for (String emailString: affiliateInviteModel.getEmails()) {
                String title = emailService.getInviteEmailTitle();
                String channelUrl = affiliateInviteModel.getChannelUrl();
                channelUrl = channelUrl != null? affiliateInviteModel.getChannelUrl() : "";

                String content = emailService.getInviteEmailContent();
                content = content.replace("[channelUrl]",channelUrl);

                String emailTemplate = emailService.getSystemEmailTemplate();
                emailTemplate = emailTemplate.replace("[title]", title);
                emailTemplate = emailTemplate.replace("[content]",content);

                Email email = MyEmailUtil.createEmailFromEmailTemplate(title ,emailTemplate, emailString, emailString);
                emails.add(email);
            }
            emailService.send(emails);
        }
        catch(Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    public void processOnAffiliateRemind(AffiliateRemindModel affiliateRemindModel, UserDto loggedUserDto) {
        logger.info("processOnAffiliateRemind : " + affiliateRemindModel + ", " + loggedUserDto);

        // put Authentication object to Spring Security Context
        Authentication authentication = TaskProcessorUtil.createAuthentication(loggedUserDto);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String errors = "";

        try {
            for (String emailString: affiliateRemindModel.getEmails()) {
                PersonDto personDto = personService.getByEmail(emailString);
                if (personDto == null || personDto.getUser().getStatus().equals(User.STATUS_ACTIVE) ||
                        !personDto.getDiscriminator().equals(PersonDto.TYPE_AFFILIATE) ) {
                    errors += "INACTIVE Affiliate '" + emailString + "' not found. ";
                }

                String name = personDto.getLastName() + " " + personDto.getFirstName();
                String title = emailService.getRemindEmailTitle();
                String channelUrl = affiliateRemindModel.getChannelUrl();
                channelUrl = channelUrl != null? "&channel_url=" + affiliateRemindModel.getChannelUrl() : "";

                String content = emailService.getRemindEmailContent();
                content = content.replace("[feUrl]",affiliateRemindModel.getFeUrl());
                content = content.replace("[activationCode]",personDto.getUser().getActivationCode());
                content = content.replace("[channelUrl]", channelUrl);

                String emailTemplate = emailService.getSystemEmailTemplate();
                emailTemplate = emailTemplate.replace("[title]", title);
                emailTemplate = emailTemplate.replace("[content]", content);

                Email email = MyEmailUtil.createEmailFromEmailTemplate(title,emailTemplate,emailString,name);
                emailService.send(email);
            }
        }
        catch(Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        if (errors != "") {
            logger.error(errors);
        }
    }

    public void processOnFeedbackCreated(FeedbackDto feedbackDto) {
        logger.info("processOnFeedbackCreated : " + feedbackDto);

        try {
            List<Email> emails = feedbackEmailProperties.getEmails()
                .stream()
                .map(email -> MyEmailUtil.createEmailFromEmailTemplate(
                        feedbackDto.getTitle(),
                        feedbackDto.getDescription(),
                        email,
                        feedbackEmailProperties.getName()
                        )
                )
                .collect(Collectors.toList());

            emailService.send(emails);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    public void processOnPaymentApproved(PaymentDto paymentDto, UserDto loggedUserDto) {
        logger.info("processOnPaymentApproved : " + paymentDto + ", " + loggedUserDto);

        // put Authentication object to Spring Security Context
        Authentication authentication = TaskProcessorUtil.createAuthentication(loggedUserDto);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // process task
        try {
            // create a Notification for Affiliate
            createNotificationOnPayment(paymentDto, EVENT_TYPE_APPROVED);

            // send Email to Affiliate
            SubsEmailTemplateDto subsEmailTemplate = subsEmailTemplateService.getByType(SubsEmailTemplate.TYPE_AFF_PAYMENT_APPROVED);
            String content = subsEmailTemplate.getContent();
            NumberFormat format = NumberFormat.getCurrencyInstance();
            String paymentAmount = format.format(paymentDto.getValue());
            paymentAmount = paymentAmount.replace("$","");
            String affiliateFullname = String.join(" ", paymentDto.getAffiliate().getLastName(), paymentDto.getAffiliate().getFirstName());
            content = content.replace("[full_name]", affiliateFullname);
            content = content.replace("[payment_amount]",paymentAmount+" VND");
            content = content.replace("[subscriber]", paymentDto.getSubscriber().getCompanyName());
            subsEmailTemplate.setContent(content);

            Email email = MyEmailUtil.createEmailFromSubsEmailTemplate(
                subsEmailTemplate, paymentDto.getAffiliate().getUser().getEmail(), affiliateFullname
            );
            emailService.send(email);
        }
        catch(Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    public void processOnOrderCreated(OrderDto orderDto, UserDto loggedUserDto) {
        logger.info("processOnOrderCreated : " + orderDto + ", " + loggedUserDto);

        // put Authentication object to Spring Security Context
        Authentication authentication = TaskProcessorUtil.createAuthentication(loggedUserDto);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // process task
        try {
            List<Email> emails = new ArrayList<>();
            if (orderDto.getAffiliate() != null) {  // Order has Seller?
                // create a Notification for Seller
                createNotificationOnOrder(orderDto, EVENT_TYPE_CREATED);

                // send an Email to Seller
                SubsEmailTemplateDto subsEmailTemplateAff = subsEmailTemplateService.getByType(SubsEmailTemplate.TYPE_AFF_ORDER_CREATED);
                String contentAff = subsEmailTemplateAff.getContent();
                String affiliateFullname = String.join(" ", orderDto.getAffiliate().getLastName(), orderDto.getAffiliate().getFirstName());
                contentAff = contentAff.replace("[full_name]", affiliateFullname);
                contentAff = contentAff.replace("[order_id]", orderDto.getNumber());
                contentAff = contentAff.replace("[channel]", orderDto.getChannel().getDomainName());
                subsEmailTemplateAff.setContent(contentAff);

                Email emailAff = MyEmailUtil.createEmailFromSubsEmailTemplate(
                    subsEmailTemplateAff, orderDto.getAffiliate().getUser().getEmail(), affiliateFullname
                );
                emails.add(emailAff);
            }

            // send an Email to Customer
            SubsEmailTemplateDto subsEmailTemplateCustomer = subsEmailTemplateService.getByType(SubsEmailTemplate.TYPE_CUSTOMER_ORDER_CREATED);
            String contentCustomer = subsEmailTemplateCustomer.getContent();
            contentCustomer = contentCustomer.replace("[full_name]", orderDto.getCustomer().getFullname());
            List<String> productNames = new ArrayList<>();
            for (OrderLineDto orderLineDto : orderDto.getOrderLines()) {
                productNames.add(orderLineDto.getProduct().getName());
            }
            contentCustomer = contentCustomer.replace("[product]", productNames.toString());
            subsEmailTemplateCustomer.setContent(contentCustomer);

            Email emailCustomer = MyEmailUtil.createEmailFromSubsEmailTemplate(
                subsEmailTemplateCustomer, orderDto.getCustomer().getEmail(),
                orderDto.getCustomer().getFullname()
            );
            emails.add(emailCustomer);

            emailService.send(emails);
        }
        catch(Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    public void processOnPaymentCreated(PaymentDto paymentDto, UserDto loggedUserDto) {
        logger.info("processOnPaymentCreated : " + paymentDto + ", " + loggedUserDto);

        // put Authentication object to Spring Security Context
        Authentication authentication = TaskProcessorUtil.createAuthentication(loggedUserDto);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // process task
        // create a Notification for Affiliate
        createNotificationOnPayment(paymentDto, EVENT_TYPE_CREATED);

        try {
            // create Notifications for SubsAdmins
            List<NotificationCreateModel> failedNotifications = new ArrayList<>();
            List<String> subsAdminIds = personService.getSubsAdminAccountantIdsBySubscriberId(paymentDto.getSubscriberId());
            for (String subsAdminId : subsAdminIds) {
                NotificationCreateModel notificationCreateModel = new NotificationCreateModel();
                notificationCreateModel.setMessage("A Payment '" + paymentDto.getValue() + "' has just been created.");
                notificationCreateModel.setToUserId(subsAdminId);
                notificationCreateModel.setType(Payment.class.getSimpleName());
                notificationCreateModel.setTargetId(paymentDto.getId());
                try {
                    notificationService.create(notificationCreateModel);
                }
                catch(Exception ex) {
                    logger.error(ex.getMessage(), ex);
                    failedNotifications.add(notificationCreateModel);
                }
            }
            if (!CollectionUtils.isEmpty(failedNotifications)) {
                createTask(Task.ACTION_CREATE_NOTIFICATIONS, Task.STATUS_NEW, MyJsonUtil.gson.toJson(failedNotifications));
            }
        }
        catch(Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    public void processOnOrderRejected(OrderDto orderDto, UserDto loggedUserDto) {
        logger.info("processOnOrderCreated : " + orderDto + ", " + loggedUserDto);

        // put Authentication object to Spring Security Context
        Authentication authentication = TaskProcessorUtil.createAuthentication(loggedUserDto);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // process task
        try {
            // create a Notification for Seller
            createNotificationOnOrder(orderDto, EVENT_TYPE_REJECTED);
        }
        catch(Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    public void processOnPaymentRejected(PaymentDto paymentDto, UserDto loggedUserDto) {
        logger.info("processOnPaymentRejected : " + paymentDto + ", " + loggedUserDto);

        // put Authentication object to Spring Security Context
        Authentication authentication = TaskProcessorUtil.createAuthentication(loggedUserDto);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // process task
        try {
            // create a Notification for Affiliate
            createNotificationOnPayment(paymentDto, EVENT_TYPE_REJECTED);
        }
        catch(Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    public void processOnSubscriberCreated(SubscriberDto subscriberDto, SubscriberCreateModel subscriberCreateModel, UserDto loggedUserDto) {
        logger.info("processOnSubscriberCreated : "+ subscriberDto + ", " + subscriberCreateModel + ", " + loggedUserDto);

        // put Authentication object to Spring Security Context
        Authentication authentication = TaskProcessorUtil.createAuthentication(loggedUserDto);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        try {
                // process channel info to Admin
                AdminDto loggedAdminDto = personService.getAdmin(loggedUserDto.getId());

                String name = loggedAdminDto.getLastName() + " " +loggedAdminDto.getFirstName();
                String title = emailService.getCreateFirstChannelEmailTitle();

                String content = emailService.getCreateFirstChannelEmailContent();
                content = content.replace("[channel_domain_name]", subscriberCreateModel.getFirstChannelDomainName());
                content = content.replace("[email]", subscriberDto.getFirstChannel().getUser().getEmail());
                content = content.replace("[password]", subscriberDto.getFirstChannel().getUser().getPassword());

                String emailTemplate = emailService.getSystemEmailTemplate();
                emailTemplate = emailTemplate.replace("[title]", title);
                emailTemplate = emailTemplate.replace("[content]", content);

                Email email = MyEmailUtil.createEmailFromEmailTemplate(title,emailTemplate,loggedAdminDto.getUser().getEmail(),name);
                emailService.send(email);
        }
        catch(Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    public void processOnSubscriberRenewed(SubscriberDto subscriberDto, UserDto loggedUserDto) {
        logger.info("processOnSubscriberRenewed : " + subscriberDto + ", " + loggedUserDto);

        // put Authentication object to Spring Security Context
        Authentication authentication = TaskProcessorUtil.createAuthentication(loggedUserDto);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        try {
            // process email
            String title = emailService.getRenewSubscriberEmailTitle();
            String content = emailService.getRenewSubscriberEmailContent();
            content = content.replace("[company_name]", subscriberDto.getCompanyName());
            content = content.replace("[domain_name]", subscriberDto.getDomainName());
            content = content.replace("[package_type]", subscriberDto.getPackageConfigApplied().getType());

            String emailTemplate = emailService.getSystemEmailTemplate();
            emailTemplate = emailTemplate.replace("[title]", title);
            emailTemplate = emailTemplate.replace("[content]", content);

            List<Email> emails = new ArrayList<>();
            for (String emailString: customerSupportEmailProperties.getEmails()) {
                Email email = MyEmailUtil.createEmailFromEmailTemplate(title,emailTemplate,emailString,customerSupportEmailProperties.getName());
                emails.add(email);
            }
            emailService.send(emails);
        }
        catch(Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    public void processOnSubscriberUpgraded(SubscriberDto subscriberDto, UserDto loggedUserDto) {
        logger.info("processOnSubscriberUpgraded : " + subscriberDto + ", " + loggedUserDto);

        // put Authentication object to Spring Security Context
        Authentication authentication = TaskProcessorUtil.createAuthentication(loggedUserDto);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        try {
            // process email
            String title = emailService.getUpgradeSubscriberEmailTitle();
            String content = emailService.getUpgradeSubscriberEmailContent();
            content = content.replace("[company_name]", subscriberDto.getCompanyName());
            content = content.replace("[domain_name]", subscriberDto.getDomainName());
            content = content.replace("[package_type]", subscriberDto.getPackageConfigApplied().getType());

            String emailTemplate = emailService.getSystemEmailTemplate();
            emailTemplate = emailTemplate.replace("[title]", title);
            emailTemplate = emailTemplate.replace("[content]", content);

            List<Email> emails = new ArrayList<>();
            for (String emailString: customerSupportEmailProperties.getEmails()) {
                Email email = MyEmailUtil.createEmailFromEmailTemplate(title,emailTemplate,emailString,customerSupportEmailProperties.getName());
                emails.add(email);
            }
            emailService.send(emails);
        }
        catch(Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    public void processOnSubsAdminCreated(SubsAdminDto subsAdminDto, SubsAdminCreateModel subsAdminCreateModel, UserDto loggedUserDto) {
        logger.info("processOnSubsAdminCreated : " + subsAdminDto + ", " + subsAdminCreateModel + ", " + loggedUserDto);

        // put Authentication object to Spring Security Context
        Authentication authentication = TaskProcessorUtil.createAuthentication(loggedUserDto);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        try {
            // process email asynchronously
            String name = subsAdminDto.getLastName() + " " + subsAdminDto.getFirstName();
            String title = emailService.getSubsAdminCreateEmailTitle();
            String content = emailService.getSubsAdminCreateEmailContent();
            content = content.replace("[email]",subsAdminCreateModel.getEmail());
            content = content.replace("[password]",subsAdminCreateModel.getPassword());
            if (!StringUtils.isBlank(subsAdminCreateModel.getFeUrl())) {
                content = content.replace("[feUrl]", subsAdminCreateModel.getFeUrl());
            } else {
                content = content.replace("[feUrl]", "http://app.rms.com.vn");
            }

            String emailTemplate = emailService.getSystemEmailTemplate();
            emailTemplate = emailTemplate.replace("[title]", title);
            emailTemplate = emailTemplate.replace("[content]",content);

            Email email = MyEmailUtil.createEmailFromEmailTemplate(title,emailTemplate,subsAdminCreateModel.getEmail(),name);
            emailService.send(email);
        }
        catch(Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    public void processOnResetPassword(ResetPasswordModel resetPasswordModel) {
        logger.info("processOnResetPassword : " + resetPasswordModel);

        try {
            String newPassword = RandomStringUtils.random(User.MINIMUM_PASSWORD_LENGTH, true, true);
            PersonDto personDto = personService.resetPassword(resetPasswordModel.getEmail(), newPassword);

            String name = String.join(" ", personDto.getLastName(), personDto.getFirstName());
            String title = emailService.getResetPasswordEmailTitle();
            String content = emailService.getResetPasswordEmailContent();
            content = content.replace("[newPassword]", newPassword);
            content = content.replace("[feUrl]", resetPasswordModel.getFeUrl());

            String emailTemplate = emailService.getSystemEmailTemplate();
            emailTemplate = emailTemplate.replace("[title]", title);
            emailTemplate = emailTemplate.replace("[content]", content);

            Email email = MyEmailUtil.createEmailFromEmailTemplate(title,emailTemplate,resetPasswordModel.getEmail(), name);
            emailService.send(email);
        }
        catch(Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    // Utilities
    private void createNotificationOnOrder(OrderDto orderDto, String eventType) {
        NotificationCreateModel notificationCreateModel = new NotificationCreateModel();
        try {
            switch (eventType) {
                case EVENT_TYPE_CREATED:
                    notificationCreateModel.setMessage("Your Order '" + orderDto.getNumber() + "' has just been created.");
                    break;

                case EVENT_TYPE_APPROVED:
                    notificationCreateModel.setMessage("Your Order '" + orderDto.getNumber() + "' has just been approved.");
                    break;

                case EVENT_TYPE_REJECTED:
                    notificationCreateModel.setMessage("Your Order '" + orderDto.getNumber() + "' has just been rejected.");
                    break;
            }
            notificationCreateModel.setToUserId(orderDto.getAffiliate().getId());
            notificationCreateModel.setType(Order.class.getSimpleName());
            notificationCreateModel.setTargetId(orderDto.getId());
            notificationService.create(notificationCreateModel);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            List<NotificationCreateModel> failedNotifications = new ArrayList<>();
            failedNotifications.add(notificationCreateModel);
            createTask(Task.ACTION_CREATE_NOTIFICATIONS, Task.STATUS_NEW, MyJsonUtil.gson.toJson(failedNotifications));
        }
    }

    private void createNotificationOnPayment(PaymentDto paymentDto, String eventType) {
        NotificationCreateModel notificationCreateModel = new NotificationCreateModel();
        try {
            switch (eventType) {
                case EVENT_TYPE_CREATED:
                    notificationCreateModel.setMessage("Your Payment '" + paymentDto.getValue() + "' has just been created.");
                    break;

                case EVENT_TYPE_APPROVED:
                    notificationCreateModel.setMessage("Your Payment '" + paymentDto.getValue() + "' has just been approved.");
                    break;

                case EVENT_TYPE_REJECTED:
                    notificationCreateModel.setMessage("Your Payment '" + paymentDto.getValue() + "' has just been rejected.");
                    break;
            }
            notificationCreateModel.setToUserId(paymentDto.getAffiliateId());
            notificationCreateModel.setType(Payment.class.getSimpleName());
            notificationCreateModel.setTargetId(paymentDto.getId());
            notificationService.create(notificationCreateModel);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            List<NotificationCreateModel> failedNotifications = new ArrayList<>();
            failedNotifications.add(notificationCreateModel);
            createTask(Task.ACTION_CREATE_NOTIFICATIONS, Task.STATUS_NEW, MyJsonUtil.gson.toJson(failedNotifications));
        }
    }

    private void createTask(String action, String status, String params) {
        TaskCreateModel createModel = new TaskCreateModel();
        createModel.setAction(action);
        createModel.setStatus(status);
        createModel.setParams(params);
        taskService.create(createModel);
    }
}
