package com.hoang.module.es.boundary;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hoang.app.boundary.ApplicationSettings;
import com.hoang.app.dto.PagedResultDTO;
import com.hoang.app.service.CrudService;
import com.hoang.module.es.domain.Place;
import com.hoang.module.es.service.PlaceService;

/**
 * 
 * @author Hoang Truong
 */

@Service
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class PlaceFCImpl implements PlaceFC {

	protected Logger logger = Logger.getLogger(PlaceFCImpl.class);
			
	@Autowired
	private CrudService crudService;
		
	@Autowired
	private PlaceService placeService;
	
//	@Autowired
//	private HibernateTemplate hibernateTemplate;
	
	// CRUD
	public Place create(Place place) {
		logger.info("create('" + place.getName() + "')");				
		return crudService.create(place);
	}
	
	public Place read(Serializable id) {
		logger.info("read(" + id + ")");		
		return crudService.read(Place.class, id);
	}		
	
	public Place update(Place place) {
		logger.info("update(" + place.getId() + ")");				
		return crudService.update(place);
	}
	
	public void delete (Serializable id) {
		logger.info("delete(" + id + ")");		
		crudService.delete(crudService.read(Place.class, id));
	}		
	
	public List<Place> getAll() {
		logger.info("getAll()");		
		return crudService.getAll(Place.class);
	}
	
	
	// Place-specific
	public Place get(String placename) {
		logger.info("get('" + placename + "')" );        
        return placeService.get(placename);
	}	
	
	public PagedResultDTO<Place> search(int pageIndex, String name) {
		logger.info("search('" + name + "')" ); 
		return placeService.search(ApplicationSettings.getPageSize(), pageIndex, name);
	}
	
	public PagedResultDTO<Place> search(int pageSize, int pageIndex, String name) {
		logger.info("search('" + name + "')" ); 
		return placeService.search(pageSize, pageIndex, name);
	}
	

	////// Utility Methods //////
	
}
