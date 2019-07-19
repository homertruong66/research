package com.hoang.app.boundary;

import java.io.Serializable;
import java.util.List;

import com.hoang.app.domain.Person;
import com.hoang.app.dto.PagedResultDTO;
import com.hoang.app.dto.PersonDTO;
import com.hoang.app.exception.BusinessException;

/**
 * 
 * @author htruong
 *
 */

public interface PersonFC {
	// CRUD
	public Person create(PersonDTO personDTO);
	public Person read(Serializable id);	
	public Person update(PersonDTO personDTO);
	public void delete(Serializable id) throws BusinessException;	
	public List<Person> getAll();	
	
	// Person-specific
    public PagedResultDTO<Person> search(int pageIndex, String firstName);
    public PagedResultDTO<Person> search(int pageSize, int pageIndex, String firstName);
}
