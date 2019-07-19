package com.hoang.lsp.dao;

import java.math.BigInteger;

import com.hoang.lsp.model.Link;

/**
 * The interface Redirections dao.
 */
public interface LinkDao {

    /**
     * Fetch by id and account id redirection.
     *
     * @param id        the id
     * @param accountId the account id
     *
     * @return the redirection
     */
    public Link fetchByIdAndAccountId (BigInteger id, Long accountId);
}
