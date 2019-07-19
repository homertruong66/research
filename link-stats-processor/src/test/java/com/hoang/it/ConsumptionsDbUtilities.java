package com.hoang.it;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.hoang.lsp.utils.LinkStatsDbMapper;
import com.hoang.lsp.model.LinkStats;

@Component
public class ConsumptionsDbUtilities {

    @Qualifier("consumptionsJdbcTemplate")
    @Autowired
    private JdbcTemplate consumptionsJdbcTemplate;

    public LinkStats queryLinkStats (final Long accountId, final BigInteger redirectionId, String table) {
        String sql = "SELECT * FROM " + table + " WHERE account_id = ? AND redirection_id = ?";
        LinkStats linkStats = this.consumptionsJdbcTemplate.queryForObject(sql, new Object[] {accountId, redirectionId}, new LinkStatsDbMapper());
        return linkStats;
    }

    public void createLinkStatsFromArchiveDb (Long accountId, BigInteger redirectionId) {
        String sql =
                "INSERT INTO archive (account_id, hour, redirection_id, domain_id, original_url_id, share_type, updated_at, created_at) VALUES (?, ?, ?, ?, ?, ?, NOW(), NOW())";
        this.consumptionsJdbcTemplate.update(sql, new Object[] {accountId, "2015-07-03 16:08:20", redirectionId, 400, 500, "facebook"});
    }

    public void createLinkStatsFromTotalsDb (Long accountId, BigInteger redirectionId) {
        String sql = "INSERT INTO totals (account_id, redirection_id, domain_id, original_url_id, share_type, updated_at, created_at) VALUES (?, ?, ?, ?, ?, NOW(), NOW())";
        this.consumptionsJdbcTemplate.update(sql, new Object[] {accountId, redirectionId, 400, 500, "facebook"});
    }

    public void deleteLinkStatsFromArchiveDb (Long accountId, BigInteger redirectionId) {
        String sql = "DELETE FROM archive WHERE account_id = ? AND redirection_id = ?";
        this.consumptionsJdbcTemplate.update(sql, new Object[] {accountId, redirectionId});
    }

    public void deleteLinkStatsFromTotalsDb (Long accountId, BigInteger redirectionId) {
        String sql = "DELETE FROM totals WHERE account_id = ? AND redirection_id = ?";
        this.consumptionsJdbcTemplate.update(sql, new Object[] {accountId, redirectionId});
    }

}
