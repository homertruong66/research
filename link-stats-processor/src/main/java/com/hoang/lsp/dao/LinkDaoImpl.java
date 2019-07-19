package com.hoang.lsp.dao;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.hoang.lsp.model.Link;
import com.hoang.lsp.utils.LinkDbMapper;

@Repository
public class LinkDaoImpl implements LinkDao {

    @Qualifier("redirectionsJdbcTemplate")
    @Autowired
    JdbcTemplate redirectionsJdbcTemplate;

    @Qualifier("redirectionsNamedJdbcTemplate")
    @Autowired
    NamedParameterJdbcTemplate redirectionsNamedJdbcTemplate;

    @Qualifier("redirectionsMasterJdbcTemplate")
    @Autowired
    JdbcTemplate redirectionsMasterJdbcTemplate;

    @Qualifier("redirectionsMasterNamedJdbcTemplate")
    @Autowired
    NamedParameterJdbcTemplate redirectionsMasterNamedJdbcTemplate;

    @Override
    public Link fetchByIdAndAccountId (final BigInteger id, final Long accountId) {
        final String sql = "SELECT redir.*, rmt.source_tag FROM (SELECT * FROM redirections WHERE id = ? and account_id = ?) as redir " +
                           "LEFT JOIN redirection_metadata rmt ON redir.id = rmt.id";
        return redirectionsJdbcTemplate.queryForObject(sql, new Object[] { id, accountId }, new LinkDbMapper());
    }
}
