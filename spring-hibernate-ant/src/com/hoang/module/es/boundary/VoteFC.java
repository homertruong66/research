package com.hoang.module.es.boundary;

import java.io.Serializable;
import java.util.List;

import com.hoang.app.dto.PagedResultDTO;
import com.hoang.module.es.domain.Vote;
import com.hoang.module.es.dto.VoteDTO;

/**
 * 
 * @author htruong
 *
 */

public interface VoteFC {	
	// CRUD
	public Vote create(VoteDTO voteDTO);
	public Vote create(Vote vote);
	public Vote read(Serializable id);	
	public Vote update(VoteDTO voteDTO);
	public void delete(Serializable id);	
	public List<Vote> getAll();	
	
	// Vote-specific
	public PagedResultDTO<Vote> searchByEvent(int pageIndex, Long eventId);
	public PagedResultDTO<Vote> searchByEvent(int pageSize, int pageIndex, Long eventId);
}
