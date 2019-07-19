package com.hoang.module.es.service;

import org.springframework.stereotype.Service;

import com.hoang.app.dto.PagedResultDTO;
import com.hoang.module.es.domain.Vote;

/**
*
* @author Hoang Truong
*/

@Service
public interface VoteService {
    // Search
    public PagedResultDTO<Vote> searchByEvent(int pageSize, int pageIndex, Long eventId);
}
