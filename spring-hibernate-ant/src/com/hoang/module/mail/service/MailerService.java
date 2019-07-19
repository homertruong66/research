package com.hoang.module.mail.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hoang.app.dto.PagedResultDTO;
import com.hoang.module.mail.domain.Mailer;

/**
*
* @author Hoang Truong
*/

@Service
public interface MailerService {
    // Find
    public Mailer get(String name);

    // Search
    public PagedResultDTO<Mailer> search(int pageSize, int pageIndex, String name);
    public List<Mailer> parseMails(String host, String username, String password) throws IOException;
}
