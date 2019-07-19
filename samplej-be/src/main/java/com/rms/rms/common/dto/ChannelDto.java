package com.rms.rms.common.dto;

import com.rms.rms.common.BaseDto;

import java.io.Serializable;

/**
 * homertruong
 */

public class ChannelDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = 7021891791167876236L;

    private String apiKey;
    private String domainName;
    private String secretKey;
    private String subscriberId;
    private ChannelUserDto user;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    public ChannelUserDto getUser() {
        return user;
    }

    public void setUser(ChannelUserDto user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "ChannelDto{" +
                "apiKey='" + apiKey + '\'' +
                ", domainName='" + domainName + '\'' +
                ", secretKey='" + secretKey + '\'' +
                ", subscriberId='" + subscriberId + '\'' +
                '}';
    }
}