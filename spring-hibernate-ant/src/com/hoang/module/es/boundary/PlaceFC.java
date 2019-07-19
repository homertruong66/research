package com.hoang.module.es.boundary;

import java.io.Serializable;
import java.util.List;

import com.hoang.app.dto.PagedResultDTO;
import com.hoang.module.es.domain.Place;

/**
 * 
 * @author Hoang Truong
 *
 */

public interface PlaceFC {	
	// CRUD
	public Place create(Place place);
	public Place read(Serializable id);
	public Place update(Place place);
	public void delete(Serializable id);
	public List<Place> getAll();	
	
	// Place-specific
	public Place get(String name);
	public PagedResultDTO<Place> search(int pageIndex, String name);
	public PagedResultDTO<Place> search(int pageSize, int pageIndex, String name);
}
