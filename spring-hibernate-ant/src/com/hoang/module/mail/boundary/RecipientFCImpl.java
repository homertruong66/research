package com.hoang.module.mail.boundary;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hoang.app.boundary.ApplicationSettings;
import com.hoang.app.dto.PagedResultDTO;
import com.hoang.app.service.CrudService;
import com.hoang.module.mail.domain.Mailer;
import com.hoang.module.mail.domain.Recipient;
import com.hoang.module.mail.dto.RecipientDTO;
import com.hoang.module.mail.service.RecipientService;

/**
 * 
 * @author Hoang Truong
 */

@Service
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class RecipientFCImpl implements RecipientFC {

	protected Logger logger = Logger.getLogger(RecipientFCImpl.class);
					
	@Autowired
	private CrudService crudService;
		
	@Autowired
	private RecipientService recipientService;
	
//	@Autowired
//	private HibernateTemplate hibernateTemplate;
	
	// CRUD		
	public Recipient read(Serializable id) {
		logger.info("read(" + id + ")");		
		return crudService.read(Recipient.class, id);
	}	
	
	public Recipient update(RecipientDTO recipientDTO) {
		logger.info("update(" + recipientDTO.getId() + ")");
		
//		Recipient recipient = crudService.read(Recipient.class, recipientDTO.getId());
		Recipient recipient = new Recipient();
		recipient.setId(recipientDTO.getId());
		copy(recipientDTO, recipient);
		
		return crudService.update(recipient);
	}
			
	
	// Recipient-specific	
    public PagedResultDTO<Recipient> searchByMailer(int pageIndex, 
													Long mailerId, String name) {
    	logger.info("searchByMailer('" + mailerId + "', '" + name + "')" ); 
		return recipientService.searchByMailer(ApplicationSettings.getPageSize(), pageIndex, mailerId, name);
    }
    
    public PagedResultDTO<Recipient> searchByMailer(int pageSize, int pageIndex, 
													Long mailerId, String name) {
    	logger.info("searchByMailer('" + mailerId + "', '" + name + "')" ); 
    	return recipientService.searchByMailer(pageSize, pageIndex, mailerId, name);
    }
	
    public void deleteByMailer(Serializable mailerId, Serializable id) {    	
        Mailer mailer = crudService.read(Mailer.class, mailerId);
        Recipient recipient = crudService.read(Recipient.class, id);
        recipient.setMailer(null);
        mailer.getRecipients().remove(recipient);
        crudService.update(mailer);
    }

	////// Utility Methods //////
    @Transactional(propagation=Propagation.SUPPORTS)
	private void copy(RecipientDTO recipientDTO, Recipient recipient) {		
		recipient.setName(recipientDTO.getName());
		recipient.setAddress(recipientDTO.getAddress());		
		recipient.setMailer(crudService.read(Mailer.class, recipientDTO.getMailerId()));		
        if (recipientDTO.getVersion() != null) {
        	recipient.setVersion(recipientDTO.getVersion());
        }
	} 
}
