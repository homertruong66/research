package com.hoang.app.boundary;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hoang.app.domain.Province;

/**
 * 
 * @author Hoang Truong
 */

@Service
public interface ProvinceFC {
	// CRUD
	public Province read(Serializable id);
	
    // Province-specific
    public List<Province> getByCountry(Long countryId);    
}
