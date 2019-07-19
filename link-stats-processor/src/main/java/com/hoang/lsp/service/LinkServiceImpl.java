package com.hoang.lsp.service;

import java.math.BigInteger;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoang.lsp.dao.LinkDao;
import com.hoang.lsp.dao.LinkStatsCache;
import com.hoang.lsp.model.Link;

@Service("redirectionsService")
public class LinkServiceImpl implements LinkService {

    @Autowired
    private LinkDao linkDao;

    @Autowired
    private LinkStatsCache linkStatsCache;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    @Qualifier("metaDataThreadPool")
    private ThreadPoolExecutor metaDataThreadPool;

    private static final Logger LOGGER = LoggerFactory.getLogger(LinkServiceImpl.class);

    @Override
    public Link getFromRedirection (BigInteger id, Long accountId) {
        Link link = linkDao.fetchByIdAndAccountId(id, accountId);

        return link;
    }

}
