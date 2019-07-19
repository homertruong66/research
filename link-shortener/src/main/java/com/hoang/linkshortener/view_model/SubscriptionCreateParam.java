package com.hoang.linkshortener.view_model;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SubscriptionCreateParam {

    @Valid
    @JsonProperty("json")
    private SubscriptionCreateModel subscriptionCreateModel;

    public SubscriptionCreateModel getSubscriptionCreateModel () {
        return subscriptionCreateModel;
    }

    public void setSubscriptionCreateModel (SubscriptionCreateModel subscriptionCreateModel) {
        this.subscriptionCreateModel = subscriptionCreateModel;
    }
}
