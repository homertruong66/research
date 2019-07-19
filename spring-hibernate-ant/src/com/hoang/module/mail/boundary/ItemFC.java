package com.hoang.module.mail.boundary;

import java.io.Serializable;

import com.hoang.app.dto.PagedResultDTO;
import com.hoang.module.mail.domain.Item;
import com.hoang.module.mail.dto.ItemDTO;

/**
 * 
 * @author htruong
 *
 */

public interface ItemFC {	
	// CRUD	
	public Item read(Serializable id);	
	public Item update(ItemDTO itemDTO);		
	
	// Item-specific
    public PagedResultDTO<Item> searchByMailer(int pageIndex, Long mailerId, String name);
    public PagedResultDTO<Item> searchByMailer(int pageSize, int pageIndex, Long mailerId, String name);
    public void deleteByMailer(Serializable mailerId, Serializable id);
}
