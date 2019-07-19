package com.rms.rms.integration;

import com.rms.rms.common.config.properties.EmailServerProperties;
import com.rms.rms.integration.exception.LocalEmailIntegrationException;
import com.rms.rms.service.model.Email;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Optional;
import java.util.Properties;

@Component
@Scope("prototype")
public class LocalEmailClient {

    private Logger logger = Logger.getLogger(LocalEmailClient.class);

    @Autowired
    private EmailServerProperties emailServerProperties;

    public void send(Email email) throws LocalEmailIntegrationException {
        EmailServerProperties.LocalEmailProperties localEmailProperties = emailServerProperties.getLocalEmail();
        String configHost = localEmailProperties.getHost();
        Integer configPort = localEmailProperties.getPort();

        logger.debug("Setup Mail Server Properties...");
        Properties mailServerProperties = System.getProperties();
        mailServerProperties.put("mail.smtp.port", configPort);
        mailServerProperties.put("mail.smtp.auth", "true");
        mailServerProperties.put("mail.smtp.starttls.enable", "true");
        logger.debug("Mail Server Properties have been setup successfully...");

        logger.debug("Get Mail Session...");
        Session session = Session.getDefaultInstance(mailServerProperties, null);
        Transport transport = null;
        logger.debug("Mail Session has been created successfully...");
        MimeMessage message = new MimeMessage(session);
        try {
            logger.info("Send email: " + email);
            String configEmail = localEmailProperties.getEmail();
            String configPassword = localEmailProperties.getPassword();
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email.getTo().getEmail()));
            message.setSubject(email.getSubject(), "UTF-8");
            message.setFrom(new InternetAddress(Optional.ofNullable(email.getFromEmail()).orElse(configEmail), email.getFromName()));
            message.setContent(email.getHtml(), "text/html;charset=UTF-8");
            transport = session.getTransport("smtp");
            transport.connect(configHost, configEmail, configPassword);
            transport.sendMessage(message, message.getAllRecipients());
            logger.info("Send email completed");
        }
        catch (Exception ex) {
            throw new LocalEmailIntegrationException(ex);
        }
        finally {
            try {
                if ((transport != null) && (transport.isConnected())) {
                    transport.close();
                }
            }
            catch (Throwable throwable) {
                logger.warn("Error when closing transport: " + throwable.getMessage(), throwable);
            }
        }
    }
}
