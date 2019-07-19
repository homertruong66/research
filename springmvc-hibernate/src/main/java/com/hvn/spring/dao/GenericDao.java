package com.hvn.spring.dao;

import java.util.List;

import com.hvn.spring.dto.DomainDTO;
import com.hvn.spring.dto.UserDTO;
import com.hvn.spring.utils.criteria.SearchCriteria;

public interface GenericDao {

	public <T> T delete(Class<?> entityClass, long id);
	
	public <T> List<T> find(SearchCriteria criteria, Class<T> clazz);
	
	public UserDTO findByUserName(String username);
	
	public List<DomainDTO> findCountries();
	
	public List<DomainDTO> findProvinces(String countryCode);
	
	public <T> T get(Class<T> entityClass, long id);
	
	public <T> List<T> getAll(Class<T> entityClass);
	
	public <T> T save(Object entity);
}
