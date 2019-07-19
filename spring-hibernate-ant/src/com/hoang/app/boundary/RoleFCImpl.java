package com.hoang.app.boundary;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hoang.app.domain.Role;
import com.hoang.app.dto.PagedResultDTO;
import com.hoang.app.service.CrudService;

/**
 * 
 * @author Hoang Truong
 */

@Service
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class RoleFCImpl implements RoleFC {

	protected Logger logger = Logger.getLogger(RoleFCImpl.class);
			
	@Autowired
	private CrudService crudService;		
	
//	@Autowired
//	private HibernateTemplate hibernateTemplate;		

	
	// CRUD
	public Role create(Role role) {
		logger.info("create('" + role.getName() + "')");						
		return crudService.create(role);
	}
	
	public Role read(Serializable id) {
		logger.info("read(" + id + ")");		
		return crudService.read(Role.class, id);
	}
		
	public Role update(Role role) {
		logger.info("update(" + role.getId() + ")");		
		return crudService.update(role);
	}
	
	public void delete (Serializable id) {
		logger.info("delete(" + id + ")");		
		crudService.delete(crudService.read(Role.class, id));
	}		
	
	public List<Role> getAll() {
		logger.info("getAll()");		
		return crudService.getAll(Role.class);
	}
	
	public PagedResultDTO<Role> page(int pageIndex) {
		logger.info("page(" + pageIndex + ")");		
		return crudService.page(Role.class, ApplicationSettings.getPageSize(), pageIndex);
	}
	
	public PagedResultDTO<Role> page(int pageSize, int pageIndex) {
		logger.info("page(" + pageSize + ", " + pageIndex + ")");		
		return crudService.page(Role.class, pageSize, pageIndex);
	}
	
	
	// Role-specific
	

	////// Utility Methods //////

}
