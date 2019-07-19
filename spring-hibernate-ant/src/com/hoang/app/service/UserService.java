package com.hoang.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hoang.app.domain.User;
import com.hoang.app.dto.PagedResultDTO;

/**
 * 
 * @author htruong
 *
 */

@Service
public interface UserService {	
    // Find
	public User get(String username);		
    public User get(String username, String password);
    public List<User> getByPerson(Long personId);
    public List<User> getByOrganization(Long organizationId);
    
    // Search    
    public PagedResultDTO<User> search(int pageSize, int pageIndex, String username);
    
    // Other services
    public boolean isPersonInUse(Long personId);
    public boolean isOrganizationInUse(Long organizationId);
    public boolean isCurrentPasswordCorret(String username, String currentPassword);
}
