package com.rms.rms.service.model;

import com.rms.rms.persistence.BaseEntityExtensible;

import java.util.Date;

public class SubsInfusionConfig extends BaseEntityExtensible {

    private String accessToken;
    private Date expiredDate;
    private String refreshToken;
    private Subscriber subscriber;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public String toString() {
        return "SubsInfusionConfig{" +
                "accessToken='" + accessToken + '\'' +
                ", expiredDate=" + expiredDate +
                ", refreshToken='" + refreshToken + '\'' +
                '}';
    }
}