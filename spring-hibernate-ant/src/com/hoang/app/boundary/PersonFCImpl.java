package com.hoang.app.boundary;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hoang.app.domain.Person;
import com.hoang.app.dto.PagedResultDTO;
import com.hoang.app.dto.PersonDTO;
import com.hoang.app.exception.BusinessException;
import com.hoang.app.service.CrudService;
import com.hoang.app.service.PersonService;
import com.hoang.app.service.UserService;

/**
 * 
 * @author Hoang Truong
 */

@Service
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class PersonFCImpl implements PersonFC {

	protected Logger logger = Logger.getLogger(PersonFCImpl.class);
			
	@Autowired
	private CrudService crudService;
		
	@Autowired
	private PersonService personService;
	
	@Autowired
	private UserService userService;
	
//	@Autowired
//	private HibernateTemplate hibernateTemplate;		

	
	// CRUD
	public Person create(PersonDTO personDTO) {
		logger.info("create('" + personDTO.getFirstName() + "')");
		
		Person person = new Person(); 
		copy(personDTO, person);
		
		return crudService.create(person);
	}
	
	public Person read(Serializable id) {
		logger.info("read(" + id + ")");		
		return crudService.read(Person.class, id);
	}		
	
	public Person update(PersonDTO personDTO) {
		logger.info("update(" + personDTO.getId() + ")");
		
		Person person = crudService.read(Person.class, personDTO.getId());
		copy(personDTO, person);
		
		return crudService.update(person);
	}
	
	public void delete (Serializable id) throws BusinessException {
		logger.info("delete(" + id + ")");
		
		if (userService.isPersonInUse((Long) id)) {
			logger.error("Can not delete this person " +
                    	 "because it is in use by other users");
            throw new BusinessException("Can not delete this person " +
                                        "because it is in use by other users");
        } 
		crudService.delete(crudService.read(Person.class, id));
	}		
	
	public List<Person> getAll() {
		logger.info("getAll()");		
		return crudService.getAll(Person.class);
	}	
		
	
	// Person-specific	
	public PagedResultDTO<Person> search(int pageIndex, String firstName) {
		logger.info("search('" + firstName + "')" ); 
		return personService.search(ApplicationSettings.getPageSize(), pageIndex, firstName);
	}
	
	public PagedResultDTO<Person> search(int pageSize, int pageIndex, String firstName) {
		logger.info("search('" + firstName + "')" ); 
		return personService.search(pageSize, pageIndex, firstName);
	}
	

	////// Utility Methods //////
	@Transactional(propagation=Propagation.SUPPORTS)
	private void copy(PersonDTO personDTO, Person person) {		
		person.setFirstName(personDTO.getFirstName());
		person.setLastName(personDTO.getLastName());
		person.setFullName(personDTO.getFullName());
		person.setSex(personDTO.getSex());
		person.setEthnicGroup(personDTO.getEthnicGroup());
		person.setHomeAddress(personDTO.getHomeAddress());
		person.setWorkAddress(personDTO.getWorkAddress());
		person.setEmail(personDTO.getEmail());
		if (personDTO.getVersion() != null) {
			person.setVersion(personDTO.getVersion());
		}
	}
}
