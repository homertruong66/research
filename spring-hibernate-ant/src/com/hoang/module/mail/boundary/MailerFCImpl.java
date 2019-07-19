package com.hoang.module.mail.boundary;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hoang.app.boundary.ApplicationSettings;
import com.hoang.app.dto.PagedResultDTO;
import com.hoang.app.service.CrudService;
import com.hoang.module.mail.domain.Item;
import com.hoang.module.mail.domain.Mailer;
import com.hoang.module.mail.domain.Recipient;
import com.hoang.module.mail.dto.ItemDTO;
import com.hoang.module.mail.dto.RecipientDTO;
import com.hoang.module.mail.service.MailerService;

/**
 * 
 * @author Hoang Truong
 */

@Service
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class MailerFCImpl implements MailerFC {

	protected Logger logger = Logger.getLogger(MailerFCImpl.class);
			
	@Autowired
	private CrudService crudService;
		
	@Autowired
	private MailerService mailerService;
	
//	@Autowired
//	private HibernateTemplate hibernateTemplate;
	
	// CRUD
	public Mailer create(Mailer mailer) {
		logger.info("create('" + mailer.getName() + "')");				
		return crudService.create(mailer);
	}
	
	public Mailer read(Serializable id) {
		logger.info("read(" + id + ")");		
		return crudService.read(Mailer.class, id);
	}
	
	public boolean has(Serializable id) {
		logger.info("has(" + id + ")");		
		return crudService.read(Mailer.class, id) != null;
	}
	
	public Mailer update(Mailer mailer) {
		logger.info("update(" + mailer.getId() + ")");
		return crudService.update(mailer);
	}
	
	public void delete (Serializable id) {
		logger.info("delete(" + id + ")");		
		crudService.delete(crudService.read(Mailer.class, id));
	}
		
	
	// Mailer-specific
	public Mailer get(String mailername) {
		logger.info("get('" + mailername + "')" );        
        return mailerService.get(mailername);
	}	
	
	public PagedResultDTO<Mailer> search(int pageIndex, String name) {
		logger.info("search('" + name + "')" ); 
		return mailerService.search(ApplicationSettings.getPageSize(), pageIndex, name);
	}
	
	public PagedResultDTO<Mailer> search(int pageSize, int pageIndex, String name) {
		logger.info("search('" + name + "')" ); 
		return mailerService.search(pageSize, pageIndex, name);
	}
	
	public void addRecipient(RecipientDTO recipientDTO) {
		Recipient recipient = new Recipient();
		recipient.setName(recipientDTO.getName());
		recipient.setAddress(recipientDTO.getAddress());
				       
        Mailer mailer = crudService.read(Mailer.class, recipientDTO.getMailerId());
        mailer.getRecipients().add(recipient);
        recipient.setMailer(mailer);
        crudService.update(mailer);
	}
	
	public void addItem(ItemDTO itemDTO) {
		Item item = new Item();
		item.setName(itemDTO.getName());
		item.setDescription(itemDTO.getDescription());
		item.setDateCreated(itemDTO.getDateCreated());
		item.setDateModified(itemDTO.getDateModified());
				       
        Mailer mailer = crudService.read(Mailer.class, itemDTO.getMailerId());
        mailer.getItems().add(item);
        item.setMailer(mailer);
        crudService.update(mailer);
	}
	
	public List<Mailer> parseMails(String host, String username, String password) throws IOException {
    	logger.debug("parseMails('" + host + "', '" + username + "', '" + password + "')");
    	return mailerService.parseMails(host, username, password);    	
    }
	
	////// Utility Methods //////

}
