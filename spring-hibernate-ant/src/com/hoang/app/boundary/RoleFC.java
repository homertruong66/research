package com.hoang.app.boundary;

import java.io.Serializable;
import java.util.List;

import com.hoang.app.domain.Role;
import com.hoang.app.dto.PagedResultDTO;

/**
 * 
 * @author htruong
 *
 */

public interface RoleFC {
	// CRUD
	public Role create(Role role);
	public Role read(Serializable id);
	public Role update(Role role);	
	public void delete (Serializable id);		
	public List<Role> getAll();
	public PagedResultDTO<Role> page(int pageIndex);
	public PagedResultDTO<Role> page(int pageSize, int pageIndex);
}
