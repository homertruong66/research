package com.rms.rms.service;

import com.rms.rms.common.config.properties.ApplicationProperties;
import com.rms.rms.common.config.properties.EmailServerProperties;
import com.rms.rms.common.util.MyFileUtil;
import com.rms.rms.common.util.MyJsonUtil;
import com.rms.rms.common.view_model.TaskCreateModel;
import com.rms.rms.integration.LocalEmailClient;
import com.rms.rms.integration.MandrillEmailClient;
import com.rms.rms.integration.SendgridEmailClient;
import com.rms.rms.integration.exception.LocalEmailIntegrationException;
import com.rms.rms.integration.exception.SendgridIntegrationException;
import com.rms.rms.service.model.Email;
import com.rms.rms.service.model.Task;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * homertruong
 */

@Service
@Transactional(value = "transactionManager", propagation = Propagation.SUPPORTS)
public class EmailServiceImpl implements EmailService, InitializingBean {

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private EmailServerProperties emailServerProperties;

    @Autowired
    @Qualifier("emailExecutorService")
    private ExecutorService emailExecutorService;

    @Autowired
    private LocalEmailClient localEmailClient;

    @Autowired
    private MandrillEmailClient mandrillEmailClient;

    @Autowired
    private SendgridEmailClient sendgridEmailClient;

    @Autowired
    private TaskService taskService;

    private Logger logger = Logger.getLogger(EmailServiceImpl.class);

    private String emailTemplate;
    private String activateEmailContent;
    private String createFirstChannelEmailContent;
    private String createAffToReferrerEmailContent;
    private String createAffToSubsAdminEmailContent;
    private String inviteEmailContent;
    private String remindEmailContent;
    private String renewSubscriberEmailContent;
    private String resetPasswordEmailContent;
    private String sendVoucherEmailContent;
    private String subsAdminCreateEmailContent;
    private String upgradeSubscriberEmailContent;
    private Map<String,String> type2Titles;

    @Override
    public void afterPropertiesSet() throws Exception {
        InputStream in;
        String emailTemplatePath = "/email_template/";
        in = getClass().getResourceAsStream(emailTemplatePath + "system_email.html");
        emailTemplate = MyFileUtil.readText(in);

        in = getClass().getResourceAsStream(emailTemplatePath + "activate_email_content.html");
        activateEmailContent = MyFileUtil.readText(in);

        in = getClass().getResourceAsStream(emailTemplatePath + "create_first_channel_email_content.html");
        createFirstChannelEmailContent = MyFileUtil.readText(in);

        in = getClass().getResourceAsStream(emailTemplatePath + "create_aff_to_referrer_email_content.html");
        createAffToReferrerEmailContent = MyFileUtil.readText(in);

        in = getClass().getResourceAsStream(emailTemplatePath + "create_aff_to_subs_admin_email_content.html");
        createAffToSubsAdminEmailContent = MyFileUtil.readText(in);

        in = getClass().getResourceAsStream(emailTemplatePath + "invite_email_content.html");
        inviteEmailContent = MyFileUtil.readText(in);

        in = getClass().getResourceAsStream(emailTemplatePath + "remind_email_content.html");
        remindEmailContent = MyFileUtil.readText(in);

        in = getClass().getResourceAsStream(emailTemplatePath + "renew_subscriber_email_content.html");
        renewSubscriberEmailContent = MyFileUtil.readText(in);

        in = getClass().getResourceAsStream(emailTemplatePath + "reset_password_email_content.html");
        resetPasswordEmailContent = MyFileUtil.readText(in);

        in = getClass().getResourceAsStream(emailTemplatePath + "send_voucher_email_content.html");
        sendVoucherEmailContent = MyFileUtil.readText(in);

        in = getClass().getResourceAsStream(emailTemplatePath + "subadmin_create_email_content.html");
        subsAdminCreateEmailContent = MyFileUtil.readText(in);

        in = getClass().getResourceAsStream(emailTemplatePath + "upgrade_subscriber_email_content.html");
        upgradeSubscriberEmailContent = MyFileUtil.readText(in);

        in = getClass().getResourceAsStream(emailTemplatePath + "titles.html");
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF8"));
        String line;
        Map<String,String> titles = new HashMap<>();
        while ((line = reader.readLine()) != null) {
            String[] titleByType = line.split(":");
            titles.put(titleByType[0],titleByType[1]);
        }
        type2Titles = titles;
        // close input stream
        try {
            reader.close();
            in.close();
        }
        catch (Exception ex) { }
    }

    @Override
    public String getSystemEmailTemplate() {
        return emailTemplate;
    }

    @Override
    public String getActivateEmailContent() {
        return activateEmailContent;
    }

    @Override
    public String getActivateEmailTitle () {
        return type2Titles.get("CREATE_AFF");
    }

    @Override
    public String getCreateFirstChannelEmailContent() {
        return createFirstChannelEmailContent;
    }

    @Override
    public String getCreateFirstChannelEmailTitle() {
        return " ";
    }

    @Override
    public String getCreateAffToReferrerEmailContent() {
        return createAffToReferrerEmailContent;
    }

    @Override
    public String getCreateAffToReferrerEmailTitle () {
        return type2Titles.get("CREATE_AFF_TO_REFERRER");
    }


    @Override
    public String getCreateAffToSubsAdminEmailContent() {
        return createAffToSubsAdminEmailContent;
    }

    @Override
    public String getCreateAffToSubsAdminEmailTitle () {
        return type2Titles.get("CREATE_AFF_TO_SUBS_ADMIN");
    }


    @Override
    public String getInviteEmailContent() {
        return inviteEmailContent;
    }

    @Override
    public String getInviteEmailTitle () {
        return type2Titles.get("INVITE");
    }

    @Override
    public String getRemindEmailContent() {
        return remindEmailContent;
    }

    @Override
    public String getRemindEmailTitle() {
        return type2Titles.get("REMIND");
    }

    @Override
    public String getRenewSubscriberEmailContent() {
        return renewSubscriberEmailContent;
    }

    @Override
    public String getRenewSubscriberEmailTitle() {
        return type2Titles.get("RENEW_SUBSCRIBER");
    }

    @Override
    public String getResetPasswordEmailContent() {
        return resetPasswordEmailContent;
    }

    @Override
    public String getResetPasswordEmailTitle() {
        return type2Titles.get("RESET_PASSWORD");
    }

    @Override
    public String getSendVoucherEmailContent() {
        return sendVoucherEmailContent;
    }

    @Override
    public String getSendVoucherEmailTitle() {
        return type2Titles.get("SEND_VOUCHER");
    }

    @Override
    public String getSubsAdminCreateEmailContent() {
        return subsAdminCreateEmailContent;
    }

    @Override
    public String getSubsAdminCreateEmailTitle() {
        return type2Titles.get("CREATE_SUBADMIN");
    }

    @Override
    public String getUpgradeSubscriberEmailContent() {
        return upgradeSubscriberEmailContent;
    }

    @Override
    public String getUpgradeSubscriberEmailTitle() {
        return type2Titles.get("UPGRADE_SUBSCRIBER");
    }

    @Override
    public Map<String, String> getType2Titles() {
        return type2Titles;
    }

    @Override
    public void send(Email email) {
        logger.info("send: " + email);

        Boolean useLocalEmailClient = applicationProperties.getUseLocalEmailClient();
        if (useLocalEmailClient.booleanValue()) {
            emailExecutorService.submit(() -> {
                try {
                    localEmailClient.send(email);
                }
                catch (LocalEmailIntegrationException leie) {
                    logger.error("send failed: " + leie.getMessage(), leie);
                }
            });
        }
        else {
            emailExecutorService.submit(() -> {
                try {
                    sendgridEmailClient.send(email);
                }
                catch (SendgridIntegrationException sie) {
                    logger.error("send failed: " + sie.getMessage(), sie);
                    // cant parse Recipient in Email directly
                    Email.Recipient to = new Email.Recipient();
                    to.setEmail(email.getTo().getEmail());
                    to.setName(email.getTo().getName());
                    email.setTo(to);
                    List<Email> failedEmails = new ArrayList<>();
                    failedEmails.add(email);
                    createTask(Task.ACTION_SEND_EMAILS, Task.STATUS_NEW, MyJsonUtil.gson.toJson(failedEmails));
                }
            });
        }
    }

    @Override
    public void send(List<Email> emails) {
        logger.info("send: " + emails);

        Boolean useLocalEmailClient = applicationProperties.getUseLocalEmailClient();
        if (useLocalEmailClient.booleanValue()) {
            emailExecutorService.submit(() -> {
                for (Email email : emails) {
                    try {
                        localEmailClient.send(email);
                    }
                    catch (LocalEmailIntegrationException leie) {
                        logger.error("send failed: " + leie.getMessage(), leie);
                    }
                }
            });
        }
        else {
            emailExecutorService.submit( () -> {
                List<Email> failedEmails = new ArrayList<>();
                for (Email email : emails) {
                    try {
                        sendgridEmailClient.send(email);
                    }
                    catch (SendgridIntegrationException sie) {
                        logger.error("send failed: " + sie.getMessage(), sie);
                        // cant parse Recipient in Email directly
                        Email.Recipient to = new Email.Recipient();
                        to.setEmail(email.getTo().getEmail());
                        to.setName(email.getTo().getName());
                        email.setTo(to);
                        failedEmails.add(email);
                    }
                }
                if (!CollectionUtils.isEmpty(failedEmails)) {
                    createTask(Task.ACTION_SEND_EMAILS, Task.STATUS_NEW, MyJsonUtil.gson.toJson(failedEmails));
                }
            });
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
