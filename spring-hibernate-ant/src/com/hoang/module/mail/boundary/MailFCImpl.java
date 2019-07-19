package com.hoang.module.mail.boundary;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hoang.app.service.CrudService;
import com.hoang.module.mail.domain.Mail;

/**
 * 
 * @author Hoang Truong
 */

@Service
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class MailFCImpl implements MailFC {

	protected Logger logger = Logger.getLogger(MailFCImpl.class);
	
	@Autowired
	private CrudService crudService;						
	
//	@Autowired
//	private HibernateTemplate hibernateTemplate;
	
	// CRUD
	public Mail read(Serializable id) {
		logger.info("read(" + id + ")");		
		return crudService.read(Mail.class, id);
	}
			
	public Mail update(Mail mail) {
		logger.info("update(" + mail.getId() + ")");						
		return crudService.update(mail);
	}
		
    //////Utility Methods //////
    
}
