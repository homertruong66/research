package com.hoang.lsp.model;

import java.math.BigInteger;
import java.util.Calendar;

public class Link {

    private BigInteger id;
    private Long       contentId;
    private String     parentAwesm;
    private String     stub;
    private String     url;
    private String     originalUrl;
    private String     shareType;
    private String     createType;
    private String     snowballId;
    private Calendar   createdAt;
    private Calendar   updatedAt;
    private Long       accountId;
    private String     sanitizedUrl;
    private String     domain;
    private String     userId;
    private String     notes;
    private BigInteger parentId;
    private BigInteger originalUrlId;
    private Long       domainId;
    private String     postId;
    private String     conversion1;
    private String     conversion2;
    private String     tag2;
    private String     tag3;
    private String     tag4;
    private String     tag5;
    private String     sourceTag;
    private BigInteger campaignId;

    public Link () {
    }

    public BigInteger getId () {
        return id;
    }

    public void setId (BigInteger id) {
        this.id = id;
    }

    public Long getContentId () {
        return contentId;
    }

    public void setContentId (Long contentId) {
        this.contentId = contentId;
    }

    public String getParentAwesm () {
        return parentAwesm;
    }

    public void setParentAwesm (String parentAwesm) {
        this.parentAwesm = parentAwesm;
    }

    public String getStub () {
        return stub;
    }

    public void setStub (String stub) {
        this.stub = stub;
    }

    public String getUrl () {
        return url;
    }

    public void setUrl (String url) {
        this.url = url;
    }

    public String getOriginalUrl () {
        return originalUrl;
    }

    public void setOriginalUrl (String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getShareType () {
        return shareType;
    }

    public void setShareType (String shareType) {
        this.shareType = shareType;
    }

    public String getCreateType () {
        return createType;
    }

    public void setCreateType (String createType) {
        this.createType = createType;
    }

    public String getSnowballId () {
        return snowballId;
    }

    public void setSnowballId (String snowballId) {
        this.snowballId = snowballId;
    }

    public Calendar getCreatedAt () {
        return createdAt;
    }

    public void setCreatedAt (Calendar createdAt) {
        this.createdAt = createdAt;
    }

    public Calendar getUpdatedAt () {
        return updatedAt;
    }

    public void setUpdatedAt (Calendar updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getAccountId () {
        return accountId;
    }

    public void setAccountId (Long accountId) {
        this.accountId = accountId;
    }

    public String getSanitizedUrl () {
        return sanitizedUrl;
    }

    public void setSanitizedUrl (String sanitizedUrl) {
        this.sanitizedUrl = sanitizedUrl;
    }

    public String getDomain () {
        return domain;
    }

    public void setDomain (String domain) {
        this.domain = domain;
    }

    public String getUserId () {
        return userId;
    }

    public void setUserId (String userId) {
        this.userId = userId;
    }

    public String getNotes () {
        return notes;
    }

    public void setNotes (String notes) {
        this.notes = notes;
    }

    public BigInteger getParentId () {
        return parentId;
    }

    public void setParentId (BigInteger parentId) {
        this.parentId = parentId;
    }

    public BigInteger getOriginalUrlId () {
        return originalUrlId;
    }

    public void setOriginalUrlId (BigInteger originalUrlId) {
        this.originalUrlId = originalUrlId;
    }

    public Long getDomainId () {
        return domainId;
    }

    public void setDomainId (Long domainId) {
        this.domainId = domainId;
    }

    public String getPostId () {
        return postId;
    }

    public void setPostId (String postId) {
        this.postId = postId;
    }

    public String getConversion1 () {
        return conversion1;
    }

    public void setConversion1 (String conversion1) {
        this.conversion1 = conversion1;
    }

    public String getConversion2 () {
        return conversion2;
    }

    public void setConversion2 (String conversion2) {
        this.conversion2 = conversion2;
    }

    public String getTag2 () {
        return tag2;
    }

    public void setTag2 (String tag2) {
        this.tag2 = tag2;
    }

    public String getTag3 () {
        return tag3;
    }

    public void setTag3 (String tag3) {
        this.tag3 = tag3;
    }

    public String getTag4 () {
        return tag4;
    }

    public void setTag4 (String tag4) {
        this.tag4 = tag4;
    }

    public String getTag5 () {
        return tag5;
    }

    public void setTag5 (String tag5) {
        this.tag5 = tag5;
    }

    public String getSourceTag () {
        return sourceTag;
    }

    public void setSourceTag (String sourceTag) {
        this.sourceTag = sourceTag;
    }

    public BigInteger getCampaignId () {
        return campaignId;
    }

    public void setCampaignId (BigInteger campaignId) {
        this.campaignId = campaignId;
    }

}
