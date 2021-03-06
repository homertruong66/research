package com.hoang.module.mail.service;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hoang.app.dto.PagedResultDTO;
import com.hoang.app.service.CrudService;
import com.hoang.app.util.DbUtil;
import com.hoang.module.mail.domain.Item;
import com.hoang.module.mail.domain.Mailer;

/**
*
* @author Hoang Truong
*/

@Service
@Transactional(propagation=Propagation.MANDATORY)
public class MailerServiceImpl implements MailerService  {
	
	private Logger logger = Logger.getLogger(MailerServiceImpl.class);
	
    @Autowired
    private CrudService crudService;
    
	@Autowired
	private HibernateTemplate hibernateTemplate;	
		
    // Finding
    @SuppressWarnings("unchecked")
	public Mailer get(String name) {
    	logger.debug("get('" + name + "')" );
		
    	String selQuery = 	"from Mailer m " +
    						"where m.name = " + name;
        List<Mailer> results = hibernateTemplate.find(selQuery);
        if (results == null || results.size() == 0) {
            return null;
        }

        return results.get(0);        
    }

    // Paging
    public PagedResultDTO<Mailer> search(int pageSize, int pageIndex, String name) {
		 logger.debug("search('" + name + "')" );
		 
		 String countQuery = 	"select count(*) " +
				         		"from Mailer m " +
				         		"where m.name like '%" + name + "%' ";

		 DetachedCriteria criteria = DetachedCriteria.forClass(Mailer.class);
		 criteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
		 criteria.addOrder(Order.asc("name"));

		 return DbUtil.search(hibernateTemplate, pageSize, pageIndex, countQuery, null, criteria); 
    }
    
    public List<Mailer> parseMails(String host, String username, String password) throws IOException {
    	logger.debug("parseMails('" + host + "', '" + username + "', '" + password + "')");
    	
        List<Mailer> mailers = new ArrayList<Mailer>();

        Store store = null;
        Folder folder = null;

        // get mails and parse them
        try {
            // Get session
            Session session = Session.getInstance(System.getProperties(), null);

            // Get the store
            store = session.getStore("pop3");
            try {
                logger.info("Connecting to " + host + " / " + username + "...");
                store.connect(host, username, password);
            }
            catch (MessagingException me ) {
                store.close();
                return null;
            }

            // Get folder
            folder = store.getFolder("INBOX");
            folder.open(Folder.READ_WRITE);

            // Get directory
            Message[] messages = folder.getMessages();
            int mesgNum = messages.length;
            if (mesgNum == 0) {
                folder.close(true);
                store.close();
                return mailers;
            }

            File mailFile = new File("mailer-list.txt");
            mailFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(mailFile);
            DataOutputStream dos = new DataOutputStream(fos);

            String line = "";
            Mailer mailer = null;
            Item item = null;
//            String sender = "";
//            String subject = "";
//            String dateSent = "";

            for (int i = 0; i < mesgNum; i++) {
               Message mesg = (Message) messages[i];
               if (mesg.isExpunged()) {
                   logger.error("Message " + mesg.getMessageNumber() + " got deleted from account " + username);
                   continue;
               }
//               sender = mesg.getFrom()[0].toString();
//               subject = mesg.getSubject();
//               dateSent = mesg.getSentDate().toString();

               String content = null;
               if (mesg.getContent() instanceof MimeMultipart) {
                    MimeMultipart contentPart = (MimeMultipart) mesg.getContent();
                    content = contentPart.getBodyPart(0).getContent().toString();
               }
               else if (mesg.getContent() instanceof String)  {
                    content = mesg.getContent().toString();
               }

               if (content == null || content.trim().equals("")) {
                   continue;
               }

               StringTokenizer stk = new StringTokenizer(content, "\n");
               while (stk.hasMoreTokens()) {
                   line = stk.nextToken().trim();
                   dos.writeBytes(line + "\n");

                   if (line.equals("")) {
                       continue;
                   }
                   else {
                       if (line.indexOf("from") != -1) {        
                           // get mailer name from mail
                           int mailerPos = line.indexOf("from") + 5;
                           String mailerName = line.substring(mailerPos, line.length()-1);

                           // check mailer
                           mailer = get(mailerName);
                           if (mailer != null) {
                               mailer.setTempItems(new HashSet<Item>(0));
                               mailers.add(mailer);
                           }
                           else {
                               System.err.println("The mailer extracted \"" + mailerName + "\" " +
                                       "does not exist in database !");
                           }
                       }

                       if (     line.length() >= 12 &&          // new item
                                line.substring(0, 12).equals("Root Item ID"))
                       {
                           if (mailer != null) {
//                               int colonPos = line.indexOf(":");
                               item = new Item();
                               item.setTransientId(new Random().nextLong());
                               item.setDateCreated((new Date()).toString());
                               item.setDateModified((new Date()).toString());
                               item.setMailer(mailer);
                               mailer.getTempItems().add(item);
                           }
                       }

                       if (line.length() >= 19 && line.substring(0, 19).equals("Customs Description")) {
                           if (item != null) {
                               int colonPos = line.indexOf(":");
                               item.setDescription(line.substring(colonPos + 2, line.length()));
                           }
                       }
                   }
               }
            }

            fos.close();
            dos.flush();
            dos.close();

            for (Mailer m : mailers) {
                for (Item i : m.getTempItems()) {
                    try {
                    	if (i.getId() == null) {
                    		crudService.create(i);
                    	}
                    	else {
                    		crudService.update(i);
                    	}
                    }
                    catch (Exception ex) {
                        logger.error("Can not save item " + i.getName() + " of mailer " + m.getName());
                        continue;
                    }
                }
            }

            // delete mails
            for (int i = 0, n = messages.length; i < n; i++) {
                logger.info(i + ": " + messages[i].getFrom()[0] + "\t" + messages[i].getSubject() + "...got deleted ! ");
                messages[i].setFlag(Flags.Flag.DELETED, true);
            }

            // close connection
            folder.close(true);
            store.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                if (store != null) {
                    store.close();
                }

                if (folder != null) {
                    folder.close(true);
                }
            }
            catch (Exception ex) {}
        }

        return mailers;
    }
}
