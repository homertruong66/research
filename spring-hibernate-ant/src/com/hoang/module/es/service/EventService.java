package com.hoang.module.es.service;

import org.springframework.stereotype.Service;

import com.hoang.app.dto.PagedResultDTO;
import com.hoang.module.es.domain.Event;

/**
*
* @author Hoang Truong
*/

@Service
public interface EventService {
    // Search
    public PagedResultDTO<Event> search(int pageSize, int pageIndex, String name);
}
