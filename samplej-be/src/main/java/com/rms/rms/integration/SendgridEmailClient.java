package com.rms.rms.integration;

import com.rms.rms.common.config.properties.EmailServerProperties;
import com.rms.rms.integration.exception.SendgridIntegrationException;
import com.rms.rms.service.model.Email;
import com.sendgrid.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class SendgridEmailClient implements InitializingBean {

    private Logger logger = Logger.getLogger(SendgridEmailClient.class);

    @Autowired
    private EmailServerProperties emailServerProperties;

    private SendGrid sendGrid;

    @Override
    public void afterPropertiesSet() {
        String apiKey = emailServerProperties.getSendgridEmail().getApiKey();
        if (apiKey == null) {
            throw new IllegalArgumentException("Sendgrid apiKey not found!");
        }

        sendGrid = new SendGrid(apiKey);
    }

    public void send(Email email) throws SendgridIntegrationException {
        logger.info("send: " + email.toString());

        String fromEmailStr = email.getFromEmail() != null? email.getFromEmail() : emailServerProperties.getSendgridEmail().getEmail();
        com.sendgrid.Email from = new com.sendgrid.Email(fromEmailStr);
        String subject = email.getSubject();
        com.sendgrid.Email to = new com.sendgrid.Email(email.getTo().getEmail());
        Content content = new Content("text/html", email.getHtml());
        Mail mail = new Mail(from, subject, to, content);

        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendGrid.api(request);
            logger.info("response statusCode: " + response.getStatusCode() + " - body: " + response.getBody() +
                        " - headers: " + response.getHeaders());
        }
        catch (Exception ex) {
            throw new SendgridIntegrationException(ex);
        }
    }

}
