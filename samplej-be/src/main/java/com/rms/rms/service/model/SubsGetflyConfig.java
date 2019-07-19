package com.rms.rms.service.model;

import com.rms.rms.persistence.BaseEntityExtensible;

public class SubsGetflyConfig extends BaseEntityExtensible {

    private String apiKey;
    private String baseUrl;
    private Subscriber subscriber;

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

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public String toString() {
        return "SubsGetflyConfig{" +
                "apiKey='" + apiKey + '\'' +
                ", baseUrl='" + baseUrl + '\'' +
                '}';
    }
}