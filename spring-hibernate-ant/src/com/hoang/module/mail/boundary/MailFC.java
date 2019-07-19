package com.hoang.module.mail.boundary;

import java.io.Serializable;

import com.hoang.module.mail.domain.Mail;

/**
 * 
 * @author htruong
 *
 */

public interface MailFC {		
	// CRUD	
	public Mail read(Serializable id);	
	public Mail update(Mail mail);	       
}
