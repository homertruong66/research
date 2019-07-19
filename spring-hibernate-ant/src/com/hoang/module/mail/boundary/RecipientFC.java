package com.hoang.module.mail.boundary;

import java.io.Serializable;

import com.hoang.app.dto.PagedResultDTO;
import com.hoang.module.mail.domain.Recipient;
import com.hoang.module.mail.dto.RecipientDTO;

/**
 * 
 * @author htruong
 *
 */

public interface RecipientFC {	
	// CRUD	
	public Recipient read(Serializable id);	
	public Recipient update(RecipientDTO recipientDTO);			
	
	// Recipient-specific
    public PagedResultDTO<Recipient> searchByMailer(int pageIndex, Long mailerId, String name);
    public PagedResultDTO<Recipient> searchByMailer(int pageSize, int pageIndex, Long mailerId, String name);
    public void deleteByMailer(Serializable mailerId, Serializable id);
}
