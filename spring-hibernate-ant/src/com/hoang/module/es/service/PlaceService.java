package com.hoang.module.es.service;

import org.springframework.stereotype.Service;

import com.hoang.app.dto.PagedResultDTO;
import com.hoang.module.es.domain.Place;

/**
*
* @author Hoang Truong
*/

@Service
public interface PlaceService {
    // Find
    public Place get(String name);

    // Search
    public PagedResultDTO<Place> search(int pageSize, int pageIndex, String name);
}
