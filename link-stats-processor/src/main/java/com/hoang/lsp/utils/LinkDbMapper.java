package com.hoang.lsp.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.hoang.lsp.model.Link;

public class LinkDbMapper implements RowMapper<Link> {

    private static final String ID_COLUMN              = "id";
    private static final String CONTENT_ID_COLUMN      = "content_id";
    private static final String PARENT_AWESM_COLUMN    = "parent_awesm";
    private static final String STUB_COLUMN            = "stub";
    private static final String URL_COLUMN             = "url";
    private static final String ORIGINAL_URL_COLUMN    = "original_url";
    private static final String SHARE_TYPE_COLUMN      = "share_type";
    private static final String CREATE_TYPE_COLUMN     = "create_type";
    private static final String SNOWBALL_ID_COLUMN     = "snowball_id";
    private static final String ACCOUNT_ID_COLUMN      = "account_id";
    private static final String SANITIZED_URL_COLUMN   = "sanitized_url";
    private static final String DOMAIN_COLUMN          = "domain";
    private static final String USER_ID_COLUMN         = "user_id";
    private static final String NOTES_COLUMN           = "notes";
    private static final String CREATED_AT_COLUMN      = "created_at";
    private static final String UPDATED_AT_COLUMN      = "updated_at";
    private static final String CAMPAIGN_ID_COLUMN     = "campaign_id";
    private static final String POST_ID_COLUMN         = "post_id";
    private static final String DOMAIN_ID_COLUMN       = "domain_id";
    private static final String ORIGINAL_URL_ID_COLUMN = "original_url_id";
    private static final String PARENT_ID_COLUMN       = "parent_id";
    private static final String CONVERSION_1_COLUMN    = "conversion_1";
    private static final String CONVERSION_2_COLUMN    = "conversion_2";
    private static final String TAG_2_COLUMN           = "tag_2";
    private static final String TAG_3_COLUMN           = "tag_3";
    private static final String TAG_4_COLUMN           = "tag_4";
    private static final String TAG_5_COLUMN           = "tag_5";
    private static final String SOURCE_TAG             = "source_tag";

    @Override
    public Link mapRow (ResultSet rs, int rowNum) throws SQLException {
        final Link link = new Link();
        link.setId(ResultSetUtils.getBigInteger(rs, ID_COLUMN));
        link.setContentId(ResultSetUtils.getLong(rs, CONTENT_ID_COLUMN));
        link.setParentAwesm(rs.getString(PARENT_AWESM_COLUMN));
        link.setStub(rs.getString(STUB_COLUMN));
        link.setUrl(rs.getString(URL_COLUMN));
        link.setOriginalUrl(rs.getString(ORIGINAL_URL_COLUMN));
        link.setShareType(rs.getString(SHARE_TYPE_COLUMN));
        link.setCreateType(rs.getString(CREATE_TYPE_COLUMN));
        link.setSnowballId(rs.getString(SNOWBALL_ID_COLUMN));
        link.setAccountId(ResultSetUtils.getLong(rs, ACCOUNT_ID_COLUMN));
        link.setSanitizedUrl(rs.getString(SANITIZED_URL_COLUMN));
        link.setDomain(rs.getString(DOMAIN_COLUMN));
        link.setUserId(rs.getString(USER_ID_COLUMN));
        link.setNotes(rs.getString(NOTES_COLUMN));
        link.setCreatedAt(ResultSetUtils.toCalendar(rs, CREATED_AT_COLUMN));
        link.setUpdatedAt(ResultSetUtils.toCalendar(rs, UPDATED_AT_COLUMN));
        link.setCampaignId(ResultSetUtils.getBigInteger(rs, CAMPAIGN_ID_COLUMN));
        link.setPostId(rs.getString(POST_ID_COLUMN));
        link.setDomainId(ResultSetUtils.getLong(rs, DOMAIN_ID_COLUMN));
        link.setOriginalUrlId(ResultSetUtils.getBigInteger(rs, ORIGINAL_URL_ID_COLUMN));
        link.setParentId(ResultSetUtils.getBigInteger(rs, PARENT_ID_COLUMN));
        link.setConversion1(rs.getString(CONVERSION_1_COLUMN));
        link.setConversion2(rs.getString(CONVERSION_2_COLUMN));
        link.setTag2(rs.getString(TAG_2_COLUMN));
        link.setTag3(rs.getString(TAG_3_COLUMN));
        link.setTag4(rs.getString(TAG_4_COLUMN));
        link.setTag5(rs.getString(TAG_5_COLUMN));
        link.setSourceTag(rs.getString(SOURCE_TAG));
        return link;
    }

}
