package com.rms.rms.integration;

import com.microtripit.mandrillapp.lutung.MandrillApi;
import com.microtripit.mandrillapp.lutung.view.MandrillMessage;
import com.microtripit.mandrillapp.lutung.view.MandrillMessageStatus;
import com.rms.rms.common.config.properties.EmailServerProperties;
import com.rms.rms.integration.exception.MandrillEmailIntegrationException;
import com.rms.rms.service.model.Email;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Scope("prototype")
public class MandrillEmailClient implements InitializingBean {

    private Logger logger = Logger.getLogger(MandrillEmailClient.class);

    @Autowired
    private EmailServerProperties emailServerProperties;

    private MandrillApi api;

    @Override
    public void afterPropertiesSet() {
        String apiKey = emailServerProperties.getMandrillEmail().getApiKey();
        if (apiKey == null) {
            throw new IllegalArgumentException("Mandrill apiKey not found!");
        }

        api = new MandrillApi(apiKey);
    }

    public void send(Email email) throws MandrillEmailIntegrationException {
        logger.info("send: " + email.toString());

        try {
            // dispatch message type
            if (email.getTemplateName() != null) {
                //dispatch to sendSms template, transactional email
                sendTransactional(email);
            }
            else {
                // dispatch to normal
                sendNormalEmail(email);
            }
        }
        catch (Exception ex) {
            throw new MandrillEmailIntegrationException(ex);
        }
    }

    public MandrillMessageStatus[] sendTransactional(Email email) throws Exception {
        MandrillMessage message = new MandrillMessage();
        message.setHeaders(email.getHeaders());

        //recipient
        MandrillMessage.Recipient to = new MandrillMessage.Recipient();
        to.setEmail(email.getTo().getEmail());

        message.setTo(Arrays.asList(to));
        message.setBcc(email.getBcc());
        message.setFromEmail(email.getFromEmail());
        message.setFromName(email.getFromName());
        message.setSubject(email.getSubject());
        message.setHtml(email.getHtml());
        message.setText(email.getText());
        List<MandrillMessage.MergeVar> globalMergeVars = new ArrayList<MandrillMessage.MergeVar>() {{
            for (Map.Entry<String, Object> entry : email.getTemplateParams().entrySet()) {
                add(new MandrillMessage.MergeVar(entry.getKey(), entry.getValue()));
            }
        }};
        message.setGlobalMergeVars(globalMergeVars);

        return api.messages().sendTemplate(email.getTemplateName(), null, message, email.getAsync());
    }

    public MandrillMessageStatus[] sendNormalEmail(Email email) throws Exception {
        //sendRaw(String fromEmail, String fromName, String rawMessage, Collection<String> to, Boolean async) throws MandrillApiError, IOException
        return api.messages().sendRaw(email.getFromEmail(), email.getFromName(), Optional.ofNullable(email.getHtml()).orElse(email.getText()), Arrays.asList(email.getTo().getEmail()), email.getAsync());
    }
}
