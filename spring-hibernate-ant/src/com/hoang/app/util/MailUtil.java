package com.hoang.app.util;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

/**
 * 
 * @author Hoang Truong
 */

public class MailUtil {
	
	private static Logger logger = Logger.getLogger(MailUtil.class);
	
	public static void sendHtmlMail(String host, String from, String displayName,
            String [] to, String subject, String htmlContent, String filename) throws Exception
	{
	    try {
	    	logger.info("sendHtmlMail('" + host + "', '" + from + "', '" + displayName + "', '" 
	        			+ to + "', '" + subject + "' ,'" + "...', '" + filename );
	
	        Properties props = new Properties();
	        props.put("mail.smtp.host", host);
	        Session session = Session.getInstance(props,null);
	        InternetAddress fromAddress = new InternetAddress(from);
	        fromAddress.setPersonal(displayName);
	        InternetAddress [] addresses = new InternetAddress[to.length];
	        for (int i = 0; i < to.length; i++) {
	            addresses[i] = new InternetAddress(to[i]);
	            
	        }
	
	        Multipart mp = new MimeMultipart();
	
	        BodyPart bodyPart = new MimeBodyPart();
	        bodyPart.setContent(htmlContent, "text/html");
	        mp.addBodyPart(bodyPart);
	
	        File file = new File(filename);
	        if (file.exists()) {
	            BodyPart filePart = new MimeBodyPart();
	            FileDataSource fds = new FileDataSource(filename);
	            filePart.setDataHandler(new DataHandler(fds));
	            filePart.setHeader("Content-ID","<image>");
	            filePart.setFileName(fds.getName());
	            mp.addBodyPart(filePart);
	        }
	
	        MimeMessage message = new MimeMessage(session);
	        message.setFrom(fromAddress);
	        message.addRecipients(Message.RecipientType.TO, addresses);
	        message.setSubject(subject);
	        message.setContent(mp);
	
	        Transport.send(message);		        
	    }
	    catch (Exception ex) {
	        throw ex;
	    }
	}
}
