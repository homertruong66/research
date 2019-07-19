package com.hoang.lsp.service;

import java.math.BigInteger;

import com.hoang.lsp.model.Link;

/**
 * The interface Redirections service.
 */
public interface LinkService {

    public Link getFromRedirection (BigInteger id, Long accountId) throws Exception;

}
