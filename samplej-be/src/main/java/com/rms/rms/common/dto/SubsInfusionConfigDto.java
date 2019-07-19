package com.rms.rms.common.dto;

import com.rms.rms.common.BaseDto;

import java.io.Serializable;

public class SubsInfusionConfigDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = 2650449192409416222L;

    private String accessToken;
    private String refreshToken;
    private SubscriberDto subscriber;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public SubscriberDto getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(SubscriberDto subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public String toString() {
        return "SubsInfusionConfigDto{" +
                '}';
    }
}