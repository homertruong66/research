package com.rms.rms.common.dto;

import com.rms.rms.common.BaseDto;

import java.io.Serializable;

public class SubsGetflyConfigDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = 5699238279603903944L;

    private String apiKey;
    private String baseUrl;
    private SubscriberDto subscriber;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public SubscriberDto getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(SubscriberDto subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public String toString() {
        return "SubsGetflyConfigDto{" +
                "apiKey='" + apiKey + '\'' +
                ", baseUrl='" + baseUrl + '\'' +
                '}';
    }
}