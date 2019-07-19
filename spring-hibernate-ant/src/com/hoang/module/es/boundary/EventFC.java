package com.hoang.module.es.boundary;

import java.io.Serializable;

import com.hoang.app.dto.PagedResultDTO;
import com.hoang.app.exception.BusinessException;
import com.hoang.module.es.domain.Event;

/**
 * 
 * @author htruong
 *
 */

public interface EventFC {
	// CRUD
	public Event create(Event event);
	public Event read(Serializable id);	
	public Event update(Event event);
	public void delete(Serializable id);
	
	// Event-specific
    public PagedResultDTO<Event> search(int pageIndex, String name);
    public PagedResultDTO<Event> search(int pageSize, int pageIndex, String name);
    public Event generateEvent();
    public void remind(Serializable id) throws BusinessException;
    public void mail(Serializable id) throws BusinessException;
}
