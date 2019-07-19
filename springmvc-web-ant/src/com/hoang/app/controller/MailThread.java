package com.hoang.app.controller;

import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.hoang.app.service.CrudService;
import com.hoang.app.util.MailUtil;
import com.hoang.module.mail.boundary.MailerFC;
import com.hoang.module.mail.domain.Item;
import com.hoang.module.mail.domain.Mail;
import com.hoang.module.mail.domain.Mailer;
import com.hoang.module.mail.domain.Recipient;

/**
 * 
 * @author Hoang Truong
 */


@Controller
public class MailThread extends Thread {

    private final Logger logger = Logger.getLogger(MailThread.class);

    @Autowired
    private CrudService crudService;
    
    @Autowired
    private MailerFC mailerFC;

//    @Autowired
//    private MailerService mailerService;
    
    public boolean isStop = false;

    public MailThread()   {
        logger.info("Thread " + getId() + " is starting...");
//        this.start();
    }

    @Override
    public void run() {
        try {
            while (!isStop) {                
                if (mailerFC == null) {
                    logger.info("Waiting for mailService initialized...");
                    Thread.sleep(10000);
                    continue;
                }

                logger.info("Thread " + this.getId() + " is checking and sending mails...");
                
                Mail mail = crudService.read(Mail.class, 1l);
                List<Mailer> mailers = mailerFC.parseMails(mail.getHost(),
                                                             mail.getUsername(),
                                                             mail.getPassword());
                if (mailers == null) {
                    logger.error("Your account or password is not correct !");
                }
                else if (mailers.size() == 0) {
                    logger.info("You have no email or there are no items in your emails");
                }
                else {                                     
                     for (Mailer mailer : mailers) {
                         for (Recipient recipient : mailer.getRecipients()) {
                             String mailContent = getMailContent(mailer.getTempItems());
                             logger.info("Start to send email to " + recipient.getName() + "/" + recipient.getAddress() +
                                     " of mailer " + mailer.getName());
                             MailUtil.sendHtmlMail(
                                        mail.getSenderHost(),
                                        mail.getSenderAddress(),
                                        mail.getSenderName(),
                                        new String [] {recipient.getAddress()},
                                        mail.getSubject(),
                                        mailContent,
                                        ""
                             );
                         }
                     }
                 }

                 logger.info("Thread " + this.getId() + " will check mail in "
                                    + mail.getTimeInterval() + " second(s)...\n");                 
                 Thread.sleep(mail.getTimeInterval() * 1000);
            }
            System.out.println("Thread stopped.");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private String getMailContent(Set<Item> items) {
        StringBuffer sb = new StringBuffer();

//        sb.append("<div align=\"right\"><img src=\"cid:image\"></div>");

        sb.append("<p>Please be advised that the following international " +
                "shipments are being held. In order to release them, " +
                "we need some additional information about your products. <br/><br/>" );
        sb.append("Please click on the Internet link for each item located " +
                "in the table below, and you will be asked to clarify " +
                "certain information about the products being shipped. " +
                "When you provide sufficient product information, a customs code will be displayed.<br/><br/>");
        sb.append("This Harmonized Code will enable your customers' orders to clear customs quickly. <p/>");

        sb.append("<style type=\"text/css\">");
        sb.append("p { font: 12px Arial; }");
        sb.append("table { width: 100%; font: 11px Arial, Helvetica, sans-serif; border-collapse: collapse; }");
        sb.append("th { text-align: left; border: 1px solid black; color: blue; padding: 0.5em 0.5em 0.5em 0.5em; }");
        sb.append("td { text-align: left; border: 1px solid black; padding: 0.5em 0.5em 0.5em 0.5em; width: auto; }");
        sb.append("</style>");
        sb.append("<table>");
        sb.append("<thead>");
        sb.append("<tr>");
        sb.append("<th>Description</th>");
        sb.append("<th>Item Number</th>");
        sb.append("<th>Link</th>");
        sb.append("</tr>");
        sb.append("</thead>");
        sb.append("<tbody>");
        for (Item item : items) {
            sb.append("<tr>");
            sb.append("<td>");
            sb.append(item.getName());
            sb.append("</td>");
            sb.append("<td>");
            sb.append(item.getDescription());
            sb.append("</td>");
            sb.append("");
            sb.append("<td>");
            sb.append("<a href=\"" + item.getDescription() + "\">");
            sb.append("Click here to provide additional product information");
            sb.append("</a>");
            sb.append("</td>");
        }
        sb.append("</tbody>");
        sb.append("</table>");
        sb.append("<p/>");

        sb.append("<p class=\"text\">Thank you for your assistance in providing us with the " +
                    "information at your earliest convenience. <br/><br/>");

        sb.append("If you have any questions or comments, please do not hesitate to contact us. <br/><br/>");        

        return sb.toString();
    }
}

/*
CPAS item harmonization and image messages were generated while processing file(s) from Delias. 

Item ID               : 156951 ML BLK
Root Item ID          : 156951 
Unit Price            : 19.5 
Country of Origin     : CN 
Harmonization Country : CA 
Item Description      : LIVIA SUPER SKINNY BELT MEDIUM-L 
Customs Description   : LIVIA SUPER SKINNY BELT MEDIUM-L 
Require Harmonization : YES 
Require Image         : False 
*/
