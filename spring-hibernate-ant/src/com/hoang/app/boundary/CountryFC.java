package com.hoang.app.boundary;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hoang.app.domain.Country;

/**
 * 
 * @author Hoang Truong
 */

@Service
public interface CountryFC {
	// CRUD
	public Country read(Serializable id);		
	public List<Country> getAll();
	public List<Country> getAllWithProvinces();
	
    // Country-specific	
    public Country get(String code);
    public Country getWithProvinces(Long id);
    public Country getWithProvinces(String code);
}
