package com.hoang.module.mail.service;

import org.springframework.stereotype.Service;

import com.hoang.app.dto.PagedResultDTO;
import com.hoang.module.mail.domain.Item;

/**
*
* @author Hoang Truong
*/

@Service
public interface ItemService {    
    // Search
    public PagedResultDTO<Item> searchByMailer(int pageSize, int pageIndex, 
                                            	Long mailerId, String name);
}
