package com.hoang.module.mail.boundary;

import java.io.Serializable;
import java.util.List;

import com.hoang.app.dto.PagedResultDTO;
import com.hoang.module.mail.domain.Mailer;
import com.hoang.module.mail.dto.ItemDTO;
import com.hoang.module.mail.dto.RecipientDTO;

/**
 * 
 * @author htruong
 *
 */

public interface MailerFC {	
	// CRUD
	public Mailer create(Mailer mailer);
	public Mailer read(Serializable id);	
	public Mailer update(Mailer mailer);
	public void delete(Serializable id);		
	
	// Mailer-specific
	public Mailer get(String name);
	public PagedResultDTO<Mailer> search(int pageIndex, String name);
	public PagedResultDTO<Mailer> search(int pageSize, int pageIndex, String name);
	public void addRecipient(RecipientDTO recipientDTO);
	public void addItem(ItemDTO itemDTO);
	public List<Mailer> parseMails(String host, String username, String password) throws Exception;
}
