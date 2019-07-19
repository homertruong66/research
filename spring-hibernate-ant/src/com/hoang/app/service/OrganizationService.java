package com.hoang.app.service;

import org.springframework.stereotype.Service;

import com.hoang.app.domain.Organization;
import com.hoang.app.dto.PagedResultDTO;

/**
 * 
 * @author Hoang Truong
 */

@Service
public interface OrganizationService {  
    // Search
    public PagedResultDTO<Organization> search(int pageSize, int pageIndex, String name);
}
