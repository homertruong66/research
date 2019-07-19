package com.hoang.app.boundary;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hoang.app.domain.Organization;
import com.hoang.app.dto.OrganizationDTO;
import com.hoang.app.dto.PagedResultDTO;
import com.hoang.app.exception.BusinessException;
import com.hoang.app.service.CrudService;
import com.hoang.app.service.OrganizationService;
import com.hoang.app.service.UserService;

/**
 * 
 * @author Hoang Truong
 */

@Service
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class OrganizationFCImpl implements OrganizationFC {

	protected Logger logger = Logger.getLogger(OrganizationFCImpl.class);
			
	@Autowired
	private CrudService crudService;
		
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private UserService userService;
	
//	@Autowired
//	private HibernateTemplate hibernateTemplate;		

	
	// CRUD
	public Organization create(OrganizationDTO organizationDTO) {
		logger.info("create('" + organizationDTO.getName() + "')");
		
		Organization organization = new Organization(); 
		copy(organizationDTO, organization);
		
		return crudService.create(organization);
	}
	
	public Organization read(Serializable id) {
		logger.info("read(" + id + ")");		
		return crudService.read(Organization.class, id);
	}		
	
	public Organization update(OrganizationDTO organizationDTO) {
		logger.info("update(" + organizationDTO.getId() + ")");
		
		Organization organization = crudService.read(Organization.class, organizationDTO.getId());
		copy(organizationDTO, organization);
		
		return crudService.update(organization);
	}
	
	public void delete (Serializable id) throws BusinessException {
		logger.info("delete(" + id + ")");	
		
		if (userService.isOrganizationInUse((Long) id)) {
			logger.error("Can not delete this organization " +
                         "because it is in use by other users");
            throw new BusinessException("Can not delete this organization " +
                                        "because it is in use by other users");
	    } 
		crudService.delete(crudService.read(Organization.class, id));
	}		
	
	public List<Organization> getAll() {
		logger.info("getAll()");		
		return crudService.getAll(Organization.class);
	}		
		
	
	// Organization-specific	
	public PagedResultDTO<Organization> search(int pageIndex, String firstName) {
		logger.info("search('" + firstName + "')" ); 
		return organizationService.search(ApplicationSettings.getPageSize(), pageIndex, firstName);
	}
	
	public PagedResultDTO<Organization> search(int pageSize, int pageIndex, String firstName) {
		logger.info("search('" + firstName + "')" ); 
		return organizationService.search(pageSize, pageIndex, firstName);
	}
	

	////// Utility Methods //////
	@Transactional(propagation=Propagation.SUPPORTS)
	private void copy(OrganizationDTO organizationDTO, Organization organization) {		
		organization.setName(organizationDTO.getName());		
		organization.setAddress(organizationDTO.getAddress());		
		organization.setEmail(organizationDTO.getEmail());       
		if (organizationDTO.getVersion() != null) {
			organization.setVersion(organizationDTO.getVersion());
		}
	}
}
