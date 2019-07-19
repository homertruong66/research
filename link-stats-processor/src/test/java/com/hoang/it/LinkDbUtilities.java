package com.hoang.it;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.hoang.lsp.model.Link;

@Component
public class LinkDbUtilities {

    @Qualifier("redirectionsJdbcTemplate")
    @Autowired
    JdbcTemplate redirectionsJdbcTemplate;

    public Link createRedirection(Long accountId, BigInteger redirectionId) {
        Link link = new Link();
        link.setId(redirectionId);
        link.setAccountId(accountId);
        link.setStub("a99");
        link.setDomain("awe.sm");
        link.setDomainId(299L);
        link.setOriginalUrlId(BigInteger.valueOf(200));

        String sql = "INSERT INTO redirections (id, account_id, domain, stub, domain_id, original_url_id) VALUES (?, ?, ?, ?, ?, ?)";
        this.redirectionsJdbcTemplate.update(
                sql,
                new Object[] { link.getId(), link.getAccountId(), link.getDomain(), link.getStub(), link.getDomainId(),
                        link.getOriginalUrlId()});
        return link;
    }

    public void deleteRedirection(final BigInteger redirectionId) {
        String sql = "DELETE FROM redirections WHERE id = ?";
        this.redirectionsJdbcTemplate.update(sql, new Object[] {redirectionId});
    }

}
