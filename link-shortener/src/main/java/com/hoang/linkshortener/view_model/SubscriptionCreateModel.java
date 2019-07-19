package com.hoang.linkshortener.view_model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hoang.linkshortener.model.Subscription;
import com.hoang.linkshortener.validation.ValidDomain;

public class SubscriptionCreateModel {

    @JsonProperty
    private String name;

    @JsonProperty
    @ValidDomain
    private String hostname;

    public void copyTo (Subscription subscription) {
        subscription.setName(this.getName());
        subscription.setHostname(this.hostname);
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getHostname () {
        return hostname;
    }

    public void setHostname (String hostname) {
        this.hostname = hostname;
    }
}
