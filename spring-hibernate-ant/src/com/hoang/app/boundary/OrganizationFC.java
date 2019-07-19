package com.hoang.app.boundary;

import java.io.Serializable;
import java.util.List;

import com.hoang.app.domain.Organization;
import com.hoang.app.dto.OrganizationDTO;
import com.hoang.app.dto.PagedResultDTO;
import com.hoang.app.exception.BusinessException;

/**
 * 
 * @author htruong
 *
 */

public interface OrganizationFC {
	// CRUD
	public Organization create(OrganizationDTO organizationDTO);
	public Organization read(Serializable id);	
	public Organization update(OrganizationDTO organizationDTO);
	public void delete(Serializable id) throws BusinessException;
	public List<Organization> getAll();	
	
	// Organization-specific
    public PagedResultDTO<Organization> search(int pageIndex, String name);
    public PagedResultDTO<Organization> search(int pageSize, int pageIndex, String name);
}
