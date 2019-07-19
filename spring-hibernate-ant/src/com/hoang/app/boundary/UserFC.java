package com.hoang.app.boundary;

import java.io.Serializable;
import java.util.List;

import com.hoang.app.domain.User;
import com.hoang.app.dto.PagedResultDTO;
import com.hoang.app.dto.UserDTO;

/**
 * 
 * @author htruong
 *
 */

public interface UserFC {	
	// CRUD
	public User create(UserDTO userDTO);
	public User read(Serializable id);	
	public User update(UserDTO userDTO);
	public void delete(Serializable id);
	public List<User> getAll();
	public List<User> getAllWithRoles();
	
	// User-specific
	public User get(String username);
	public User getWithRoles(Serializable id);
	public User getWithRoles(String username);	
	public PagedResultDTO<User> search(int pageIndex, String username);
	public PagedResultDTO<User> search(int pageSize, int pageIndex, String username);
	public PagedResultDTO<User> searchWithRoles(int pageIndex, String username);
	public PagedResultDTO<User> searchWithRoles(int pageSize, int pageIndex, String username);
	public boolean isCurrentPasswordCorret(String username, String currentPassword);
	public void changePassword(UserDTO userDTO);    
    public void enable(String username);
    public void disable(String username);
}
