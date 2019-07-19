package com.hoang.lsp.events;

import java.math.BigInteger;
import java.util.Calendar;

import com.hoang.lsp.core.GoalType;
import com.hoang.lsp.core.dataformat.deserializer.UnixTimestampDeserializer;
import com.hoang.lsp.utils.DateTimeUtils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class ConversionEvent {

    @JsonProperty
    private String id;

    @JsonProperty("account_id")
    private Long accountId;

    @JsonProperty("redirection_id")
    private BigInteger redirectionId;

    @JsonProperty("awesm_url")
    private String awesmUrl;

    @JsonProperty("clicker_id")
    private String clickerId;

    @JsonProperty("session_id")
    private String sessionId;

    @JsonProperty
    private GoalType type;

    @JsonProperty
    private Long value;

    @JsonProperty
    private String href;

    @JsonProperty("account_userid")
    private String accountUserId;

    @JsonProperty("account_conversionid")
    private String accountConversionId;

    @JsonProperty("ip_address")
    private String ipAddress;

    @JsonProperty("user_agent")
    private String userAgent;

    @JsonProperty
    private String referrer;

    @JsonProperty
    private String language;

    @JsonProperty("converted_at")
    @JsonDeserialize(using = UnixTimestampDeserializer.class)
    private Calendar convertedAt;

    public ConversionEvent () {
    }

    public String getId () {
        return id;
    }

    public void setId (String id) {
        this.id = id;
    }

    public Long getAccountId () {
        return accountId;
    }

    public void setAccountId (Long accountId) {
        this.accountId = accountId;
    }

    public BigInteger getRedirectionId () {
        return redirectionId;
    }

    public void setRedirectionId (BigInteger redirectionId) {
        this.redirectionId = redirectionId;
    }

    public String getAwesmUrl () {
        return awesmUrl;
    }

    public void setAwesmUrl (String awesmUrl) {
        this.awesmUrl = awesmUrl;
    }

    public String getClickerId () {
        return clickerId;
    }

    public void setClickerId (String clickerId) {
        this.clickerId = clickerId;
    }

    public String getSessionId () {
        return sessionId;
    }

    public void setSessionId (String sessionId) {
        this.sessionId = sessionId;
    }

    public GoalType getType () {
        return type;
    }

    public void setType (GoalType type) {
        this.type = type;
    }

    public Long getValue () {
        return value;
    }

    public void setValue (Long value) {
        this.value = value;
    }

    public String getHref () {
        return href;
    }

    public void setHref (String href) {
        this.href = href;
    }

    public String getAccountUserId () {
        return accountUserId;
    }

    public void setAccountUserId (String accountUserId) {
        this.accountUserId = accountUserId;
    }

    public String getAccountConversionId () {
        return accountConversionId;
    }

    public void setAccountConversionId (String accountConversionId) {
        this.accountConversionId = accountConversionId;
    }

    public String getIpAddress () {
        return ipAddress;
    }

    public void setIpAddress (String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserAgent () {
        return userAgent;
    }

    public void setUserAgent (String userAgent) {
        this.userAgent = userAgent;
    }

    public String getReferrer () {
        return referrer;
    }

    public void setReferrer (String referrer) {
        this.referrer = referrer;
    }

    public String getLanguage () {
        return language;
    }

    public void setLanguage (String language) {
        this.language = language;
    }

    public Calendar getConvertedAt () {
        return convertedAt;
    }

    public void setConvertedAt (Calendar convertedAt) {
        this.convertedAt = convertedAt;
    }

    @Override
    public String toString () {
        final StringBuilder sb = new StringBuilder();
        sb.append("ConversionEvent").append("[");
        sb.append("redirectionId=").append(redirectionId);
        sb.append(", id=").append(id);
        sb.append(", accountId=").append(accountId);
        sb.append(", awesmUrl=").append(awesmUrl);
        sb.append(", clickerId=").append(clickerId);
        sb.append(", sessionId=").append(sessionId);
        sb.append(", type=").append(type);
        sb.append(", value=").append(value);
        sb.append(", href=").append(href);
        sb.append(", accountUserid=").append(accountUserId);
        sb.append(", accountConversionid=").append(accountConversionId);
        sb.append(", ipAddress=").append(ipAddress);
        sb.append(", userAgent=").append(userAgent);
        sb.append(", referrer=").append(referrer);
        sb.append(", language=").append(language);
        sb.append(", convertedAt=").append(convertedAt != null ? DateTimeUtils.parseToString(convertedAt) : "null");
        sb.append("]");
        return sb.toString();
    }

}
