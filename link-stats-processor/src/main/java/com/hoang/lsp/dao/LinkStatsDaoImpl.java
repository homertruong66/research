package com.hoang.lsp.dao;

import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.hoang.lsp.core.Column;
import com.hoang.lsp.core.GoalType;
import com.hoang.lsp.core.MetricType;
import com.hoang.lsp.model.LinkStats;
import com.hoang.lsp.utils.ConvertUtils;
import com.hoang.lsp.utils.IncrementFieldRowMapper;
import com.hoang.lsp.utils.LastClickRowMapper;
import com.hoang.lsp.utils.LinkStatsDbMapper;
import com.hoang.lsp.utils.QueryUtils;

@Repository
public class LinkStatsDaoImpl implements LinkStatsDao {

    @Qualifier("consumptionsJdbcTemplate")
    @Autowired
    private JdbcTemplate consumptionsJdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${consumptions.db.insert.batch}")
    private int batch;

    public static String FIRST_STATEMENT = "INSERT INTO link_stats_increments" +
                            "(" +
                            "account_id, redirection_id, awesm_id, clicked_date, " +
                            "clicks_increment, multi_gen_clicks_increment, " +
                            "goal_1_increment, multi_gen_goal_1_increment, goal_1_value_increment, multi_gen_goal_1_value_increment, " +
                            "goal_2_increment, multi_gen_goal_2_increment, goal_2_value_increment, multi_gen_goal_2_value_increment, " +
                            "goal_3_increment, multi_gen_goal_3_increment, goal_3_value_increment, multi_gen_goal_3_value_increment, " +
                            "goal_4_increment, multi_gen_goal_4_increment, goal_4_value_increment, multi_gen_goal_4_value_increment, " +
                            "goal_5_increment, multi_gen_goal_5_increment, goal_5_value_increment, multi_gen_goal_5_value_increment, " +
                            "goal_6_increment, multi_gen_goal_6_increment, goal_6_value_increment, multi_gen_goal_6_value_increment, " +
                            "goal_7_increment, multi_gen_goal_7_increment, goal_7_value_increment, multi_gen_goal_7_value_increment, " +
                            "goal_8_increment, multi_gen_goal_8_increment, goal_8_value_increment, multi_gen_goal_8_value_increment, " +
                            "goal_9_increment, multi_gen_goal_9_increment, goal_9_value_increment, multi_gen_goal_9_value_increment, " +
                            "goal_10_increment, multi_gen_goal_10_increment, goal_10_value_increment, multi_gen_goal_10_value_increment, " +
                            "pageviews_increment, multi_gen_pageviews_increment, " +
                            "created_at" +
                            ") " +
                            "VALUES ";

    @Override
    public Integer getHourlyClickIncrementingValue(final Long accountId, final BigInteger redirectionId, final String roundedCreatedAt) {
        return getHourlyIncrementingValue(Column.CLICKS, accountId, redirectionId, roundedCreatedAt);
    }
    
    @Override
    public Integer getHourlyGoalIncrementingValue(Long accountId, BigInteger redirectionId, String roundedCreatedAt, GoalType goalType) {
        return getHourlyIncrementingValue(getGoalColumnBy(goalType), accountId, redirectionId, roundedCreatedAt);
    }
    
    @Override
    public Integer getHourlyGoalValueIncrementingValue(Long accountId, BigInteger redirectionId, String roundedCreatedAt, GoalType goalType) {
        return getHourlyIncrementingValue(getGoalValueColumnBy(goalType), accountId, redirectionId, roundedCreatedAt);
    }
    
    @Override
    public String getLastClick(final Long accountId, final BigInteger redirectionId) {
        final String sql = "SELECT last_clicked_at as incr_field FROM totals WHERE account_id = ? AND redirection_id = ?";
        List<String> lastClickResults = consumptionsJdbcTemplate.query(sql, new Object[] {accountId, redirectionId}, new LastClickRowMapper("incr_field"));
        return DataAccessUtils.singleResult(lastClickResults);
    }

    @Override
    public Integer getTotalsClickIncrementingValue(final Long accountId, final BigInteger redirectionId) {
        return getTotalIncrementingValue(Column.CLICKS, accountId, redirectionId);
    }

    @Override
    public Integer getTotalsGoalIncrementingValue(Long accountId, BigInteger redirectionId, GoalType goalType) {
        return getTotalIncrementingValue(getGoalColumnBy(goalType), accountId, redirectionId);
    }

    @Override
    public Integer getTotalsGoalValueIncrementingValue(Long accountId, BigInteger redirectionId, GoalType goalType) {
        return getTotalIncrementingValue(getGoalValueColumnBy(goalType), accountId, redirectionId);
    }

    @Override
    public List<LinkStats> queryLinkStatsClicksByHour (Calendar hour) {
        final String sql = "SELECT account_id, hour, redirection_id, clicks FROM archive WHERE hour = ?";
        List<LinkStats> linkStats = this.consumptionsJdbcTemplate.query(sql, new Object[] {hour}, new LinkStatsDbMapper());
        return linkStats;
    }

    @Override
    public void saveHourlyLinkStatsBatch (MetricType metricType, List<LinkStats> linkStatsList) {
        String sql = null;
        String sqlTemplate = null;
        Column column = getColumnBy(metricType);
        switch (metricType) {
            case CLICK:
            case PAGE_VIEW:
                sqlTemplate = "INSERT INTO archive ({0}, account_id, hour, redirection_id, domain_id, campaign_id, " + "original_url_id, share_type, create_type, sharer_id, parent_id, " +
                              "user_id, notes, account_userid_id, post_id, reach, shared_at, " + "tag_2, tag_3, tag_4, tag_5, updated_at, created_at) " +
                              "VALUES ({1}, :accountId, :hour, :redirectionId, :domainId, :campaignId, " +
                              ":originalUrlId, :shareType, :createType, :sharerId, :parentId, :userId, " +
                              ":notes, :accountUserId, :postId, :reach, :sharedAt, :tag2, :tag3, :tag4, " + ":tag5, NOW(), NOW()) " +
                              "ON DUPLICATE KEY UPDATE {0} = COALESCE({0}, 0) + COALESCE({1}, 0)";
                sql = MessageFormat.format(sqlTemplate, column.getName(), getColumnParameterBy(column));
                break;
            case GOAL_1:
            case GOAL_2:
            case GOAL_3:
            case GOAL_4:
            case GOAL_5:
            case GOAL_6:
            case GOAL_7:
            case GOAL_8:
            case GOAL_9:
            case GOAL_10:
                Column goalValueColumn = getGoalValueColumnBy(ConvertUtils.convertToGoalType(metricType));
                sqlTemplate = "INSERT INTO archive ({0}, {2}, account_id, hour, redirection_id, domain_id, campaign_id, " +
                              "original_url_id, share_type, create_type, sharer_id, parent_id, " + "user_id, notes, account_userid_id, post_id, reach, shared_at, " +
                              "tag_2, tag_3, tag_4, tag_5, updated_at, created_at) " +
                              "VALUES ({1}, {3}, :accountId, :hour, :redirectionId, :domainId, :campaignId, " +
                              ":originalUrlId, :shareType, :createType, :sharerId, :parentId, :userId, " +
                              ":notes, :accountUserId, :postId, :reach, :sharedAt, :tag2, :tag3, :tag4, " + ":tag5, NOW(), NOW()) " +
                              "ON DUPLICATE KEY UPDATE {0} = COALESCE({0}, 0) + COALESCE({1}, 0), {2} = COALESCE({2}, 0) + COALESCE({3}, 0)";
                sql = MessageFormat.format(sqlTemplate, column.getName(), getColumnParameterBy(column), goalValueColumn.getName(), getColumnParameterBy(goalValueColumn));
                break;

            default:
                throw new IllegalArgumentException("invalid metricType:" + metricType);
        }

        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(linkStatsList.toArray());
        namedParameterJdbcTemplate.batchUpdate(sql, batch);
    }

    @Override
    public void saveIncrements (final Map<BigInteger, List<LinkStats>> shares) {
        List<String> queries = QueryUtils.buildQueryForIncrement(FIRST_STATEMENT, shares);
        List<List<String>> subList = Lists.partition(queries, batch);
        for (List<String> query : subList) {
            consumptionsJdbcTemplate.batchUpdate(Iterables.toArray(query, String.class));
        }
    }

    @Override
    public void saveTotalsLinkStatsBatch (MetricType metricType, List<LinkStats> linkStatsList) {
        String sql = null;
        String sqlTemplate = null;
        Column column = getColumnBy(metricType);
        switch (metricType) {
            case CLICK:
            case PAGE_VIEW:
                sqlTemplate = "INSERT INTO totals ({0}, account_id, redirection_id, domain_id, campaign_id, " + "original_url_id, share_type, create_type, sharer_id, parent_id, " +
                              "user_id, notes, account_userid_id, post_id, reach, shared_at, " + "tag_2, tag_3, tag_4, tag_5, updated_at, created_at) " +
                              "VALUES ({1}, :accountId, :redirectionId, :domainId, :campaignId, " + ":originalUrlId, :shareType, :createType, :sharerId, :parentId, :userId, " +
                              ":notes, :accountUserId, :postId, :reach, :sharedAt, :tag2, :tag3, :tag4, " + ":tag5, NOW(), NOW()) " +
                              "ON DUPLICATE KEY UPDATE {0} = COALESCE({0}, 0) + COALESCE({1}, 0)";
                sql = MessageFormat.format(sqlTemplate, column.getName(), getColumnParameterBy(column));
                break;
            case LAST_CLICKED_AT:
                sqlTemplate = "INSERT INTO totals ({0}, account_id, redirection_id, domain_id, campaign_id, " + "original_url_id, share_type, create_type, sharer_id, parent_id, " +
                              "user_id, notes, account_userid_id, post_id, reach, shared_at, " + "tag_2, tag_3, tag_4, tag_5, updated_at, created_at) " +
                              "VALUES ({1}, :accountId, :redirectionId, :domainId, :campaignId, " + ":originalUrlId, :shareType, :createType, :sharerId, :parentId, :userId, " +
                              ":notes, :accountUserId, :postId, :reach, :sharedAt, :tag2, :tag3, :tag4, " + ":tag5, NOW(), NOW()) " +
                              "ON DUPLICATE KEY UPDATE {0} = IF( {0} IS NULL OR TIMESTAMP({1}) > TIMESTAMP({0}), {1}, {0})";
                sql = MessageFormat.format(sqlTemplate, column.getName(), getColumnParameterBy(column));
                break;
            case GOAL_1:
            case GOAL_2:
            case GOAL_3:
            case GOAL_4:
            case GOAL_5:
            case GOAL_6:
            case GOAL_7:
            case GOAL_8:
            case GOAL_9:
            case GOAL_10:
                Column goalValueColumn = getGoalValueColumnBy(ConvertUtils.convertToGoalType(metricType));
                sqlTemplate = "INSERT INTO totals ({0}, {2}, account_id, redirection_id, domain_id, campaign_id, " + "original_url_id, share_type, create_type, sharer_id, parent_id, " +
                              "user_id, notes, account_userid_id, post_id, reach, shared_at, " + "tag_2, tag_3, tag_4, tag_5, updated_at, created_at) " +
                              "VALUES ({1}, {3}, :accountId, :redirectionId, :domainId, :campaignId, " +
                              ":originalUrlId, :shareType, :createType, :sharerId, :parentId, :userId, " +
                              ":notes, :accountUserId, :postId, :reach, :sharedAt, :tag2, :tag3, :tag4, " + ":tag5, NOW(), NOW()) " +
                              "ON DUPLICATE KEY UPDATE {0} = COALESCE({0}, 0) + COALESCE({1}, 0), {2} = COALESCE({2}, 0) + COALESCE({3}, 0)";
                sql = MessageFormat.format(sqlTemplate, column.getName(), getColumnParameterBy(column), goalValueColumn.getName(), getColumnParameterBy(goalValueColumn));
                break;

            default:
                throw new IllegalArgumentException("invalid metricType:" + metricType);
        }

        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(linkStatsList.toArray());
        namedParameterJdbcTemplate.batchUpdate(sql, batch);
    }


    ////// 	Utilities 	//////
    private Column getColumnBy(MetricType metricType) {
        switch (metricType) {
            case CLICK:
                return Column.CLICKS;
            case LAST_CLICKED_AT:
                return Column.LAST_CLICKED_AT;
            case PAGE_VIEW:
            case GOAL_1:
            case GOAL_2:
            case GOAL_3:
            case GOAL_4:
            case GOAL_5:
            case GOAL_6:
            case GOAL_7:
            case GOAL_8:
            case GOAL_9:
            case GOAL_10:
                return getGoalColumnBy(ConvertUtils.convertToGoalType(metricType));

            default:
                throw new IllegalArgumentException("invalid metricType:" + metricType);
        }
    }

    private String getColumnParameterBy(Column column) {
        switch (column) {
            case CLICKS:
                return ":clicksIncrement";
            case LAST_CLICKED_AT:
                return ":lastClickedAt";
            case PAGE_VIEWS:
                return ":pageViewsIncrement";
            case GOAL_1:
                return ":goal1Increment";
            case GOAL_1_VALUE:
                return ":goal1ValueIncrement";
            case GOAL_2:
                return ":goal2Increment";
            case GOAL_2_VALUE:
                return ":goal2ValueIncrement";
            case GOAL_3:
                return ":goal3Increment";
            case GOAL_3_VALUE:
                return ":goal3ValueIncrement";
            case GOAL_4:
                return ":goal4Increment";
            case GOAL_4_VALUE:
                return ":goal4ValueIncrement";
            case GOAL_5:
                return ":goal5Increment";
            case GOAL_5_VALUE:
                return ":goal5ValueIncrement";
            case GOAL_6:
                return ":goal6Increment";
            case GOAL_6_VALUE:
                return ":goal6ValueIncrement";
            case GOAL_7:
                return ":goal7Increment";
            case GOAL_7_VALUE:
                return ":goal7ValueIncrement";
            case GOAL_8:
                return ":goal8Increment";
            case GOAL_8_VALUE:
                return ":goal8ValueIncrement";
            case GOAL_9:
                return ":goal9Increment";
            case GOAL_9_VALUE:
                return ":goal9ValueIncrement";
            case GOAL_10:
                return ":goal10Increment";
            case GOAL_10_VALUE:
                return ":goal10ValueIncrement";

            default:
                throw new IllegalArgumentException("invalid column:" + column);
        }
    }

    private Column getGoalColumnBy(GoalType goalType) {
        switch (goalType) {
            case PAGE_VIEW:
                return Column.PAGE_VIEWS;
            case GOAL_1:
                return Column.GOAL_1;
            case GOAL_2:
                return Column.GOAL_2;
            case GOAL_3:
                return Column.GOAL_3;
            case GOAL_4:
                return Column.GOAL_4;
            case GOAL_5:
                return Column.GOAL_5;
            case GOAL_6:
                return Column.GOAL_6;
            case GOAL_7:
                return Column.GOAL_7;
            case GOAL_8:
                return Column.GOAL_8;
            case GOAL_9:
                return Column.GOAL_9;
            case GOAL_10:
                return Column.GOAL_10;

            default:
                throw new IllegalArgumentException("invalid goalType:" + goalType);
        }
    }

    private Column getGoalValueColumnBy(GoalType goalType) {
        switch (goalType) {
            case PAGE_VIEW:
                return Column.PAGE_VIEWS;
            case GOAL_1:
                return Column.GOAL_1_VALUE;
            case GOAL_2:
                return Column.GOAL_2_VALUE;
            case GOAL_3:
                return Column.GOAL_3_VALUE;
            case GOAL_4:
                return Column.GOAL_4_VALUE;
            case GOAL_5:
                return Column.GOAL_5_VALUE;
            case GOAL_6:
                return Column.GOAL_6_VALUE;
            case GOAL_7:
                return Column.GOAL_7_VALUE;
            case GOAL_8:
                return Column.GOAL_8_VALUE;
            case GOAL_9:
                return Column.GOAL_9_VALUE;
            case GOAL_10:
                return Column.GOAL_10_VALUE;

            default:
                throw new IllegalArgumentException("invalid goalType:" + goalType);
        }
    }

    private Integer getHourlyIncrementingValue(final Column column, final Long accountId, final BigInteger redirectionId, final String roundedCreatedAt) {
        final String sql = MessageFormat.format("SELECT {0} as incr_field FROM archive WHERE account_id = ? AND hour = ? AND redirection_id = ?", column.getName());
        List<Integer> results = consumptionsJdbcTemplate.query(sql, new Object[] {accountId, roundedCreatedAt, redirectionId}, new IncrementFieldRowMapper("incr_field"));
        return DataAccessUtils.singleResult(results);
    }

    private Integer getTotalIncrementingValue(final Column column, final Long accountId, final BigInteger redirectionId) {
        final String sql = MessageFormat.format("SELECT {0} as incr_field FROM totals WHERE account_id = ? AND redirection_id = ?", column.getName());
        List<Integer> results = consumptionsJdbcTemplate.query(sql, new Object[] {accountId, redirectionId}, new IncrementFieldRowMapper("incr_field"));
        return DataAccessUtils.singleResult(results);
    }

}
