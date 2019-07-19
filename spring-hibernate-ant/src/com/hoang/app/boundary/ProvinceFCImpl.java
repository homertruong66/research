package com.hoang.app.boundary;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hoang.app.domain.Country;
import com.hoang.app.domain.Province;
import com.hoang.app.service.CrudService;

/**
 * 
 * @author Hoang Truong
 */

@Service
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class ProvinceFCImpl implements ProvinceFC {
	
	private Logger logger = Logger.getLogger(ProvinceFCImpl.class);		
	
	@Autowired
	private CrudService crudService;
	
	// CRUD
	public Province read(Serializable id) {
		logger.info("read(" + id + ")");		
		return crudService.read(Province.class, id);
	}		
	
    // Province-specific
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Province> getByCountry(Long countryId) {
    	logger.info("getByCountry(" + countryId + ")" );
    	
        List<Province> results = new ArrayList<Province>();
        Country country = crudService.read(Country.class, countryId);
        if (country != null) {
            for (Province province : country.getProvinces()) {
                results.add(province);
            }
            
            Collections.sort(results, new Comparator() {
                public int compare(Object o1, Object o2) {
                    Province p1 = (Province) o1;
                    Province p2 = (Province) o2;
                    return p1.getName().compareTo(p2.getName());
                }
            });
        }      

        return results;
    }        
}
