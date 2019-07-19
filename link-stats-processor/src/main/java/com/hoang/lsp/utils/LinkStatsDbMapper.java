package com.hoang.lsp.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.hoang.lsp.model.LinkStats;
import com.hoang.lsp.utils.NumberUtils;
import com.hoang.lsp.utils.ResultSetUtils;

public class LinkStatsDbMapper implements RowMapper<LinkStats> {

    private static final String ACCOUNT_ID_COLUMN = "account_id";
    private static final String HOUR_COLUMN = "hour";
    private static final String REDIRECTION_ID_COLUMN = "redirection_id";
    private static final String CLICKS_COLUMN = "clicks";
    private static final String DOMAIN_ID_COLUMN = "domain_id";
    private static final String CAMPAIGN_ID_COLUMN = "campaign_id";
    private static final String ORIGINAL_URL_ID_COLUMN = "original_url_id";
    private static final String SHARE_TYPE_COLUMN = "share_type";
    private static final String CREATE_TYPE_COLUMN = "create_type";
    private static final String SHARER_ID_COLUMN = "sharer_id";
    private static final String PARENT_ID_COLUMN = "parent_id";
    private static final String USER_ID_COLUMN = "user_id";
    private static final String NOTES_COLUMN = "notes";
    private static final String POST_ID_COLUMN = "post_id";
    private static final String ACCOUNT_USERID_COLUMN = "account_userid_id";
    private static final String PAGEVIEWS_COLUMN = "pageviews";
    private static final String GOAL_1_COLUMN = "goal_1";
    private static final String GOAL_1_VALUE_COLUMN = "goal_1_value";
    private static final String GOAL_2_COLUMN = "goal_2";
    private static final String GOAL_2_VALUE_COLUMN = "goal_2_value";
    private static final String GOAL_3_COLUMN = "goal_3";
    private static final String GOAL_3_VALUE_COLUMN = "goal_3_value";
    private static final String GOAL_4_COLUMN = "goal_4";
    private static final String GOAL_4_VALUE_COLUMN = "goal_4_value";
    private static final String GOAL_5_COLUMN = "goal_5";
    private static final String GOAL_5_VALUE_COLUMN = "goal_5_value";
    private static final String GOAL_6_COLUMN = "goal_6";
    private static final String GOAL_6_VALUE_COLUMN = "goal_6_value";
    private static final String GOAL_7_COLUMN = "goal_7";
    private static final String GOAL_7_VALUE_COLUMN = "goal_7_value";
    private static final String GOAL_8_COLUMN = "goal_8";
    private static final String GOAL_8_VALUE_COLUMN = "goal_8_value";
    private static final String GOAL_9_COLUMN = "goal_9";
    private static final String GOAL_9_VALUE_COLUMN = "goal_9_value";
    private static final String GOAL_10_COLUMN = "goal_10";
    private static final String GOAL_10_VALUE_COLUMN = "goal_10_value";
    private static final String MENTIONS_COLUMN = "mentions";
    private static final String REACH_COLUMN = "reach";
    private static final String REACTIONS_1_COLUMN = "reactions_1";
    private static final String REACTIONS_2_COLUMN = "reactions_2";
    private static final String SHARED_AT_COLUMN = "shared_at";
    private static final String UPDATED_AT_COLUMN = "updated_at";
    private static final String CREATED_AT_COLUMN = "created_at";
    private static final String TAG_2_COLUMN = "tag_2";
    private static final String TAG_3_COLUMN = "tag_3";
    private static final String TAG_4_COLUMN = "tag_4";
    private static final String TAG_5_COLUMN = "tag_5";

    @Override
    public LinkStats mapRow(ResultSet rs, int rowNum) throws SQLException {
        final LinkStats linkStats = new LinkStats();
        linkStats.setHour(ResultSetUtils.toCalendar(rs, HOUR_COLUMN));
        linkStats.setAccountId(ResultSetUtils.getLong(rs, ACCOUNT_ID_COLUMN));
        linkStats.setRedirectionId(NumberUtils.toBigInteger(ResultSetUtils.getLong(rs, REDIRECTION_ID_COLUMN)));
        linkStats.setClicksIncrement(ResultSetUtils.getInt(rs, CLICKS_COLUMN));
        linkStats.setDomainId(ResultSetUtils.getLong(rs, DOMAIN_ID_COLUMN));
        linkStats.setCampaignId(NumberUtils.toBigInteger(ResultSetUtils.getLong(rs, CAMPAIGN_ID_COLUMN)));
        linkStats.setOriginalUrlId(NumberUtils.toBigInteger(ResultSetUtils.getLong(rs, ORIGINAL_URL_ID_COLUMN)));
        linkStats.setShareType(ResultSetUtils.getString(rs, SHARE_TYPE_COLUMN));
        linkStats.setCreateType(ResultSetUtils.getString(rs, CREATE_TYPE_COLUMN));
        linkStats.setSharerId(ResultSetUtils.getString(rs, SHARER_ID_COLUMN));
        linkStats.setParentId(NumberUtils.toBigInteger(ResultSetUtils.getLong(rs, PARENT_ID_COLUMN)));
        linkStats.setUserId(ResultSetUtils.getString(rs, USER_ID_COLUMN));
        linkStats.setNotes(ResultSetUtils.getString(rs, NOTES_COLUMN));
        linkStats.setPostId(ResultSetUtils.getLong(rs, POST_ID_COLUMN));
        linkStats.setAccountUserId(ResultSetUtils.getLong(rs, ACCOUNT_USERID_COLUMN));
        linkStats.setPageViewsIncrement(ResultSetUtils.getInt(rs, PAGEVIEWS_COLUMN));
        linkStats.setGoal1Increment(ResultSetUtils.getInt(rs, GOAL_1_COLUMN));
        linkStats.setGoal1ValueIncrement(ResultSetUtils.getInt(rs, GOAL_1_VALUE_COLUMN));
        linkStats.setGoal2Increment(ResultSetUtils.getInt(rs, GOAL_2_COLUMN));
        linkStats.setGoal2ValueIncrement(ResultSetUtils.getInt(rs, GOAL_2_VALUE_COLUMN));
        linkStats.setGoal3Increment(ResultSetUtils.getInt(rs, GOAL_3_COLUMN));
        linkStats.setGoal3ValueIncrement(ResultSetUtils.getInt(rs, GOAL_3_VALUE_COLUMN));
        linkStats.setGoal4Increment(ResultSetUtils.getInt(rs, GOAL_4_COLUMN));
        linkStats.setGoal4ValueIncrement(ResultSetUtils.getInt(rs, GOAL_4_VALUE_COLUMN));
        linkStats.setGoal5Increment(ResultSetUtils.getInt(rs, GOAL_5_COLUMN));
        linkStats.setGoal5ValueIncrement(ResultSetUtils.getInt(rs, GOAL_5_VALUE_COLUMN));
        linkStats.setGoal6Increment(ResultSetUtils.getInt(rs, GOAL_6_COLUMN));
        linkStats.setGoal6ValueIncrement(ResultSetUtils.getInt(rs, GOAL_6_VALUE_COLUMN));
        linkStats.setGoal7Increment(ResultSetUtils.getInt(rs, GOAL_7_COLUMN));
        linkStats.setGoal7ValueIncrement(ResultSetUtils.getInt(rs, GOAL_7_VALUE_COLUMN));
        linkStats.setGoal8Increment(ResultSetUtils.getInt(rs, GOAL_8_COLUMN));
        linkStats.setGoal8ValueIncrement(ResultSetUtils.getInt(rs, GOAL_8_VALUE_COLUMN));
        linkStats.setGoal9Increment(ResultSetUtils.getInt(rs, GOAL_9_COLUMN));
        linkStats.setGoal9ValueIncrement(ResultSetUtils.getInt(rs, GOAL_9_VALUE_COLUMN));
        linkStats.setGoal10Increment(ResultSetUtils.getInt(rs, GOAL_10_COLUMN));
        linkStats.setGoal10ValueIncrement(ResultSetUtils.getInt(rs, GOAL_10_VALUE_COLUMN));
        linkStats.setMentions(ResultSetUtils.getInt(rs, MENTIONS_COLUMN));
        linkStats.setReach(ResultSetUtils.getInt(rs, REACH_COLUMN));
        linkStats.setReactions1(ResultSetUtils.getInt(rs, REACTIONS_1_COLUMN));
        linkStats.setReaations2(ResultSetUtils.getInt(rs, REACTIONS_2_COLUMN));
        linkStats.setSharedAt(ResultSetUtils.toCalendar(rs, SHARED_AT_COLUMN));
        linkStats.setUpdatedAt(ResultSetUtils.toCalendar(rs, UPDATED_AT_COLUMN));
        linkStats.setCreatedAt(ResultSetUtils.toCalendar(rs, CREATED_AT_COLUMN));
        linkStats.setTag2(ResultSetUtils.getString(rs, TAG_2_COLUMN));
        linkStats.setTag3(ResultSetUtils.getString(rs, TAG_3_COLUMN));
        linkStats.setTag4(ResultSetUtils.getString(rs, TAG_4_COLUMN));
        linkStats.setTag5(ResultSetUtils.getString(rs, TAG_5_COLUMN));
        return linkStats;
    }

}
