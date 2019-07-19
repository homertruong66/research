package com.hoang.module.mail.service;

import org.springframework.stereotype.Service;

import com.hoang.app.dto.PagedResultDTO;
import com.hoang.module.mail.domain.Recipient;

/**
 * 
 * @author Hoang Truong
 */

@Service
public interface RecipientService {    
    // Search
    public PagedResultDTO<Recipient> searchByMailer(int pageSize, int pageIndex,
                                                 	Long mailerId, String name);
}
