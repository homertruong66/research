package com.hoang.module.es.boundary;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hoang.app.boundary.ApplicationSettings;
import com.hoang.app.dto.PagedResultDTO;
import com.hoang.app.service.CrudService;
import com.hoang.module.es.domain.Place;
import com.hoang.module.es.domain.Vote;
import com.hoang.module.es.dto.VoteDTO;
import com.hoang.module.es.service.PlaceService;
import com.hoang.module.es.service.VoteService;

/**
 * 
 * @author Hoang Truong
 */

@Service
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class VoteFCImpl implements VoteFC {

	protected Logger logger = Logger.getLogger(VoteFCImpl.class);
			
	@Autowired
	private CrudService crudService;
		
	@Autowired
	private VoteService voteService;
	
	@Autowired
	private PlaceService placeService;
	
//	@Autowired
//	private HibernateTemplate hibernateTemplate;
	
	// CRUD
	public Vote create(VoteDTO voteDTO) {
		logger.info("create('" + voteDTO.getDate() + "')");
		
		Vote vote = new Vote(); 
		copy(voteDTO, vote);
		
		return crudService.create(vote);
	}
	
	public Vote create(Vote vote) {
		logger.info("create('" + vote.getDate() + "')");				
		return crudService.create(vote);
	}
	
	public Vote read(Serializable id) {
		logger.info("read(" + id + ")");		
		return crudService.read(Vote.class, id);
	}		
	
	public Vote update(VoteDTO voteDTO) {
		logger.info("update(" + voteDTO.getId() + ")");
		
		Vote vote = crudService.read(Vote.class, voteDTO.getId());
		copy(voteDTO, vote);
		
		return crudService.update(vote);
	}
	
	public void delete (Serializable id) {
		logger.info("delete(" + id + ")");		
		crudService.delete(crudService.read(Vote.class, id));
	}		
	
	public List<Vote> getAll() {
		logger.info("getAll()");		
		return crudService.getAll(Vote.class);
	}
			
	
	// Vote-specific
	public PagedResultDTO<Vote> searchByEvent(int pageIndex, Long eventId) {
		logger.info("searchByEvent('" + eventId + "')" ); 
		return voteService.searchByEvent(ApplicationSettings.getPageSize(), pageIndex, eventId);
	}
	
	public PagedResultDTO<Vote> searchByEvent(int pageSize, int pageIndex, Long eventId) {
		logger.info("searchByEvent('" + eventId + "')" ); 
		return voteService.searchByEvent(pageSize, pageIndex, eventId);
	}
	

	////// Utility Methods //////
	@Transactional(propagation=Propagation.SUPPORTS)
	private void copy(VoteDTO voteDTO, Vote vote) {				
        vote.setDate(voteDTO.getDate());
        vote.setEvent(voteDTO.getEvent());
        vote.setUser(voteDTO.getUser());
        vote.getPlaces().clear();
        Set<String> strPlaces = voteDTO.getPlaces(); 
        if (strPlaces != null) {        	
            for (String strPlace : strPlaces) {
            	Place place = placeService.get(strPlace);
                if (place != null) {
                    vote.getPlaces().add(place);
                }
            }
        }
        if (voteDTO.getVersion() != null) {
        	vote.setVersion(voteDTO.getVersion());
        }
	}
}
