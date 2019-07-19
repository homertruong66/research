package com.hoang.app.service;

import java.io.Serializable;
import java.util.List;

import com.hoang.app.dto.PagedResultDTO;

/**
 * 
 * @author Hoang Truong
 */

public interface CrudService {

	public <T> T create(T pdo);
	public <T> T read(Class<T> clazz, Serializable id);
	public <T> boolean has(Class<T> clazz, Serializable id);
	public <T> T update(T pdo);
	public <T> void delete (T pdo);	
	public <T> Long countAll(Class<T> clazz);
	public <T> List<T> getAll(Class<T> clazz);
	public <T> PagedResultDTO<T> page(Class<T> clazz, int pageSize, int pageIndex);	
}
