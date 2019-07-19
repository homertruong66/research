package com.hoang.app.boundary;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hoang.app.domain.Role;
import com.hoang.app.domain.User;
import com.hoang.app.domain.UserRole;
import com.hoang.app.dto.PagedResultDTO;
import com.hoang.app.dto.UserDTO;
import com.hoang.app.service.CrudService;
import com.hoang.app.service.RoleService;
import com.hoang.app.service.UserService;

/**
 * 
 * @author Hoang Truong
 */

@Service
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class UserFCImpl implements UserFC {

	protected Logger logger = Logger.getLogger(UserFCImpl.class);
			
	@Autowired
	private CrudService crudService;
		
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
//	@Autowired
//	private HibernateTemplate hibernateTemplate;		

	
	// CRUD
	public User create(UserDTO userDTO) {
		logger.info("create('" + userDTO.getUsername() + "')");
		
		User user = new User(); 
		copy(userDTO, user);
		
		return crudService.create(user);
	}
	
	public User read(Serializable id) {
		logger.info("read(" + id + ")");		
		return crudService.read(User.class, id);
	}		
	
	public User update(UserDTO userDTO) {
		logger.info("update(" + userDTO.getId() + ")");
		
		User user = crudService.read(User.class, userDTO.getId());
		copy(userDTO, user);
		
		return crudService.update(user);
	}
	
	public void delete (Serializable id) {
		logger.info("delete(" + id + ")");		
		crudService.delete(crudService.read(User.class, id));
	}		
		
	public List<User> getAll() {
		logger.info("getAll()");
		return crudService.getAll(User.class);
	}
	
	public List<User> getAllWithRoles() {
		logger.info("getAllWithRoles()");
		List<User> users = crudService.getAll(User.class);
		for (User user : users) {
			user.getUserRoles().size(); 	// get userRoles lazily then roles eagerly
		}
		
		return users;
	}
	
	// User-specific
	public User get(String username) {
		logger.info("get('" + username + "')" );        
        return userService.get(username);
	}	
	
	public User getWithRoles(Serializable id) {
		logger.info("getWithRoles('" + id + "')" );
		User user = crudService.read(User.class, id);
		user.getUserRoles().size(); 	// get userRoles lazily then roles eagerly
		
		return user;
	}
	
	public User getWithRoles(String username) {
		logger.info("getWithRoles('" + username + "')" ); 
		User user = userService.get(username);
		user.getUserRoles().size(); 	// get userRoles lazily then roles eagerly
		
		return user;
	}		
	
	public PagedResultDTO<User> search(int pageIndex, String username) {
		logger.info("search('" + username + "')" ); 
		return userService.search(ApplicationSettings.getPageSize(), pageIndex, username);
	}
	
	public PagedResultDTO<User> search(int pageSize, int pageIndex, String username) {
		logger.info("search('" + username + "')" ); 
		return userService.search(pageSize, pageIndex, username);
	}
	
	public PagedResultDTO<User> searchWithRoles(int pageIndex, String username) {
		logger.info("searchWithRoles('" + username + "')" );
		PagedResultDTO<User> pr = userService.search(ApplicationSettings.getPageSize(), pageIndex, username);
		for (User user : pr.getPagedResults()) {
			user.getUserRoles().size(); 	// get userRoles lazily then roles eagerly
		}
		
		return pr;
	}
	
	public PagedResultDTO<User> searchWithRoles(int pageSize, int pageIndex, String username) {
		logger.info("searchWithRoles('" + username + "')" ); 
		PagedResultDTO<User> pr = userService.search(pageSize, pageIndex, username);
		for (User user : pr.getPagedResults()) {
			user.getUserRoles().size(); 	// get userRoles lazily then roles eagerly
		}
		
		return pr;
	}
	
	public boolean isCurrentPasswordCorret(String username, String currentPassword) {
		logger.info("isCurrentPasswordCorrect('" + username + "', '" + currentPassword + "')" );
		return userService.isCurrentPasswordCorret(username, currentPassword);
	}
    
    public void changePassword(UserDTO userDTO) {
    	logger.info("changePassword('" + userDTO.getUsername() + "')" );
    	User user = userService.get(userDTO.getUsername());
    	if (user != null) {
    		user.setPassword(userDTO.getPassword());
    		crudService.update(user);
    	}
    }
    
    public void enable(String username) {
    	logger.info("enable('" + username + "')" );
    	User user = userService.get(username);
    	if (user != null) {
    		user.setEnabled(true);
    		crudService.update(user);
    	}
    }
    
    public void disable(String username) {
    	logger.info("disable('" + username + "')" );
    	User user = userService.get(username);
    	if (user != null) {
    		user.setEnabled(false);
    		crudService.update(user);
    	}
    }
	

	////// Utility Methods //////
	@Transactional(propagation=Propagation.SUPPORTS)
	private void copy(UserDTO userDTO, User user) {		
		user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEnabled(userDTO.isEnabled());
        user.setFullname(userDTO.getFullname());
        user.setEmail(userDTO.getEmail());
        if (userDTO.isHasPerson()) {
        	user.setBelongOrganization(false);
            user.setParty(userDTO.getPerson());
        }
        else {
        	user.setBelongOrganization(true);
            user.setParty(userDTO.getOrganization());
        }        
        if (userDTO.getRoles() != null) {
        	user.getUserRoles().clear();
            for (String strRole : userDTO.getRoles()) {
                Role r = roleService.get(strRole);
                if (r != null) {
                    UserRole userRole = new UserRole();
                    userRole.setTransientId(new Random().nextLong());
                    userRole.setRole(r);
                    userRole.setExpiryDate(new Date());
                    user.getUserRoles().add(userRole);
                }
            }
        }
        if (userDTO.getVersion() != null) {
        	user.setVersion(userDTO.getVersion());
        }
	}
}
