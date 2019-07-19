package com.hoang.app.service;

import org.springframework.stereotype.Service;

import com.hoang.app.domain.Role;

/**
 * 
 * @author Hoang Truong
 */

@Service
public interface RoleService {	
	// Find
	public Role get(String name);	
}
