package com.hoang.lsp.events;

import java.math.BigInteger;
import java.util.Calendar;

import com.hoang.lsp.utils.DateTimeUtils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class ClickEvent {

    @JsonProperty("domain")
    private String domain;

    @JsonProperty("stub")
    private String stub;

    @JsonProperty("created_at")
    private Calendar createdAt;

    @JsonDeserialize()
    @JsonProperty("updated_at")
    private Calendar updatedAt;

    @JsonProperty("remote_addr")
    private String remoteAddress;

    @JsonProperty("http_referer")
    private String httpReferer;

    @JsonProperty("http_user_agent")
    private String httpUserAgent;

    @JsonProperty("http_accept_language")
    private String httpAcceptLanguage;

    @JsonProperty("snowball_id")
    private String snowballId;

    @JsonProperty("campaign_id")
    private BigInteger campaignId;

    @JsonProperty("parent_id")
    private BigInteger parentId;

    @JsonProperty("domain_id")
    private Long domainId;

    @JsonProperty("original_url_id")
    private BigInteger originalUrlId;

    @JsonProperty("redirection_id")
    private BigInteger redirectionId;

    @JsonProperty("account_id")
    private Long accountId;

    @JsonProperty("share_type")
    private String shareType;

    @JsonProperty("create_type")
    private String createType;

    @JsonProperty("sharer_id")
    private String sharerId;

    @JsonProperty("bot_flag")
    private int botFlag;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("notes")
    private String notes;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getStub() {
        return stub;
    }

    public void setStub(String stub) {
        this.stub = stub;
    }

    public Calendar getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Calendar createdAt) {
        this.createdAt = createdAt;
    }

    public Calendar getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Calendar updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public String getHttpReferer() {
        return httpReferer;
    }

    public void setHttpReferer(String httpReferer) {
        this.httpReferer = httpReferer;
    }

    public String getHttpUserAgent() {
        return httpUserAgent;
    }

    public void setHttpUserAgent(String httpUserAgent) {
        this.httpUserAgent = httpUserAgent;
    }

    public String getHttpAcceptLanguage() {
        return httpAcceptLanguage;
    }

    public void setHttpAcceptLanguage(String httpAcceptLanguage) {
        this.httpAcceptLanguage = httpAcceptLanguage;
    }

    public String getSnowballId() {
        return snowballId;
    }

    public void setSnowballId(String snowballId) {
        this.snowballId = snowballId;
    }

    public BigInteger getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(BigInteger campaignId) {
        this.campaignId = campaignId;
    }

    public BigInteger getParentId() {
        return parentId;
    }

    public void setParentId(BigInteger parentId) {
        this.parentId = parentId;
    }

    public Long getDomainId() {
        return domainId;
    }

    public void setDomainId(Long domainId) {
        this.domainId = domainId;
    }

    public BigInteger getOriginalUrlId() {
        return originalUrlId;
    }

    public void setOriginalUrlId(BigInteger originalUrlId) {
        this.originalUrlId = originalUrlId;
    }

    public BigInteger getRedirectionId() {
        return redirectionId;
    }

    public void setRedirectionId(BigInteger redirectionId) {
        this.redirectionId = redirectionId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getShareType() {
        return shareType;
    }

    public void setShareType(String shareType) {
        this.shareType = shareType;
    }

    public String getCreateType() {
        return createType;
    }

    public void setCreateType(String createType) {
        this.createType = createType;
    }

    public String getSharerId() {
        return sharerId;
    }

    public void setSharerId(String sharerId) {
        this.sharerId = sharerId;
    }

    public int getBotFlag() {
        return botFlag;
    }

    public void setBotFlag(int botFlag) {
        this.botFlag = botFlag;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "ClickEvent [domain=" + domain + ", stub=" + stub + ", createdAt=" + (createdAt != null ? DateTimeUtils.parseToString(createdAt) : "null") + ", updatedAt="
                + (updatedAt != null ? DateTimeUtils.parseToString(updatedAt) : "null") + ", remoteAddress=" + remoteAddress + ", httpReferer=" + httpReferer + ", httpUserAgent="
                + httpUserAgent + ", httpAcceptLanguage=" + httpAcceptLanguage + ", snowballId=" + snowballId + ", campaignId=" + campaignId + ", parentId=" + parentId
                + ", domainId=" + domainId + ", originalUrlId=" + originalUrlId + ", redirectionId=" + redirectionId + ", accountId=" + accountId + ", shareType=" + shareType
                + ", createType=" + createType + ", sharerId=" + sharerId + ", botFlag=" + botFlag + ", userId=" + userId + ", notes=" + notes + "]";
    }

}
