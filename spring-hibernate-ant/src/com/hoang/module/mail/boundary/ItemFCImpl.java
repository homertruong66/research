package com.hoang.module.mail.boundary;

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
import com.hoang.module.mail.dto.ItemDTO;
import com.hoang.module.mail.service.ItemService;

/**
 * 
 * @author Hoang Truong
 */

@Service
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class ItemFCImpl implements ItemFC {

	protected Logger logger = Logger.getLogger(ItemFCImpl.class);
			
	@Autowired
	private CrudService crudService;
		
	@Autowired
	private ItemService itemService;
	
//	@Autowired
//	private HibernateTemplate hibernateTemplate;
	
	// CRUD
	public Item create(ItemDTO itemDTO) {
		logger.info("create('" + itemDTO + "')");
		
		Item item = new Item(); 
		copy(itemDTO, item);
		
		return crudService.create(item);
	}
	
	public Item read(Serializable id) {
		logger.info("read(" + id + ")");		
		return crudService.read(Item.class, id);
	}
	
	public boolean has(Serializable id) {
		logger.info("has(" + id + ")");		
		return crudService.read(Item.class, id) != null;
	}
	
	public Item update(ItemDTO itemDTO) {
		logger.info("update(" + itemDTO.getId() + ")");
		
		Item item = crudService.read(Item.class, itemDTO.getId());
		copy(itemDTO, item);
		
		return crudService.update(item);
	}
		
	public Long countAll() {
		logger.info("countAll()");		
		return crudService.countAll(Item.class);
	}
	
	public List<Item> getAll() {
		logger.info("getAll()");		
		return crudService.getAll(Item.class);
	}
	
	public PagedResultDTO<Item> page(int pageIndex) {
		logger.info("page(" + pageIndex + ")");		
		return crudService.page(Item.class, ApplicationSettings.getPageSize(), pageIndex);
	}
	
	public PagedResultDTO<Item> page(int pageSize, int pageIndex) {
		logger.info("page(" + pageSize + ", " + pageIndex + ")");		
		return crudService.page(Item.class, pageSize, pageIndex);
	}
		
	
	// Item-specific	
    public PagedResultDTO<Item> searchByMailer(int pageIndex, Long mailerId, String name) {
    	logger.info("searchByMailer('" + mailerId + "', '" + name + "')" ); 
		return itemService.searchByMailer(ApplicationSettings.getPageSize(), pageIndex, mailerId, name);
    }
    
    public PagedResultDTO<Item> searchByMailer(int pageSize, int pageIndex, 
												Long mailerId, String name) {
    	logger.info("searchByMailer('" + mailerId + "', '" + name + "')" ); 
    	return itemService.searchByMailer(pageSize, pageIndex, mailerId, name);
	}
    
    public void deleteByMailer(Serializable mailerId, Serializable id) {    	
        Mailer mailer = crudService.read(Mailer.class, mailerId);
        Item item = crudService.read(Item.class, id);
        item.setMailer(null);
        mailer.getItems().remove(item);
        crudService.update(mailer);
    }
	

	////// Utility Methods //////
	@Transactional(propagation=Propagation.SUPPORTS)
	private void copy(ItemDTO itemDTO, Item item) {		
		item.setName(itemDTO.getName());
		item.setDescription(itemDTO.getDescription());
		item.setDateCreated(itemDTO.getDateCreated());
		item.setDateModified(itemDTO.getDateModified());
		if (itemDTO.getId() == null) {
			item.setMailer(crudService.read(Mailer.class, itemDTO.getMailerId()));
		}
        if (itemDTO.getVersion() != null) {
        	item.setVersion(itemDTO.getVersion());
        }
        item.setVersion(itemDTO.getVersion());                    
	}
}
