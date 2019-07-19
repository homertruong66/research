package com.hoang.app.service;

import org.springframework.stereotype.Service;

import com.hoang.app.domain.Person;
import com.hoang.app.dto.PagedResultDTO;

/**
 * 
 * @author Hoang Truong
 */

@Service
public interface PersonService {
    // Search
    public PagedResultDTO<Person> search(int pageSize, int pageIndex, String firstName);
}
