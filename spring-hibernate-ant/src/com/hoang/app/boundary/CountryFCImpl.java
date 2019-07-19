package com.hoang.app.boundary;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hoang.app.domain.Country;
import com.hoang.app.service.CrudService;

/**
 * 
 * @author Hoang Truong
 */

@Service
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class CountryFCImpl implements CountryFC {   
	
	private Logger logger = Logger.getLogger(CountryFCImpl.class);
	
	@Autowired
	private CrudService crudService;	
	
	@Autowired
	private HibernateTemplate hibernateTemplate;	
	
	// CRUD
	public Country read(Serializable id) {
		logger.info("read(" + id + ")");		
		return crudService.read(Country.class, id);
	}
		
	public List<Country> getAll() {
		logger.info("getAll()");		
		return crudService.getAll(Country.class);
	}
	
	public List<Country> getAllWithProvinces() {
		logger.info("getAllWithProvinces()");
		List<Country> countries = crudService.getAll(Country.class); 
		for (Country country : countries) {
			country.getProvinces().size(); 	// load provinces lazily
		}
		
		return countries;
	}
	
    // Country-specific    	
    @SuppressWarnings("unchecked")
	public Country get(String code) {
    	logger.info("get('" + code + "')" );
		
    	String selQuery = 	"from Country c " +
    						"where c.code = '" + code + "'";
        List<Country> results = hibernateTemplate.find(selQuery);
        if (results == null || results.size() == 0) {
            return null;
        }

        return results.get(0);
    }
        
	public Country getWithProvinces(Long id) {
		logger.info("getWithProvinces(" + id+ ")" );
		    	
        Country country = crudService.read(Country.class, id);
        country.getProvinces().size(); 		// load provinces lazily

        return country;
	}
    
    @SuppressWarnings("unchecked")
	public Country getWithProvinces(String code) {
		logger.info("getWithProvinces('" + code + "')" );
		
    	String selQuery = 	"from Country c " +
    						"where c.code = '" + code + "'";
        List<Country> results = hibernateTemplate.find(selQuery);
        if (results == null || results.size() == 0) {
            return null;
        }
        
        Country country = results.get(0);
        country.getProvinces().size(); 		// load provinces lazily

        return country;
	}	
}
