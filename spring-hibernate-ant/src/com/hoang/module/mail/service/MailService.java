package com.hoang.module.mail.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hoang.module.mail.domain.Mailer;

/**
*
* @author Hoang Truong
*/

@Service
public interface MailService {
    // Other Services    
    public List<Mailer> parseMails(String host, String username, String password) throws IOException;
    public void sendHtmlMail(String host, String from, String displayName,
                             String to, String subject, String htmlContent, String filename);
}
