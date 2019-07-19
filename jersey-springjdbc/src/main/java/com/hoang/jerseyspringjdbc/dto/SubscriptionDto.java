package com.hoang.jerseyspringjdbc.dto;

import java.io.Serializable;

import org.springframework.util.Assert;

import com.hoang.jerseyspringjdbc.model.Subscription;
import com.hoang.jerseyspringjdbc.util.DateUtils;

public class SubscriptionDto implements Serializable {

    private static final long serialVersionUID = 9207501337207736179L;

    private int    id;
    private String name;
    private int    userId;
    private String subscriptionKey;
    private String status;
    private String billingStatus;
    private String nextBillingDate;
    private String lastBillingDate;
    private String trialEndDate;
    private String createdAt;
    private String updatedAt;

    public int getId () {
        return id;
    }

    public void setId (int id) {
        this.id = id;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public int getUserId () {
        return userId;
    }

    public void setUserId (int userId) {
        this.userId = userId;
    }

    public String getSubscriptionKey () {
        return subscriptionKey;
    }

    public void setSubscriptionKey (String subscriptionKey) {
        this.subscriptionKey = subscriptionKey;
    }

    public String getStatus () {
        return status;
    }

    public void setStatus (String status) {
        this.status = status;
    }

    public String getBillingStatus () {
        return billingStatus;
    }

    public void setBillingStatus (String billingStatus) {
        this.billingStatus = billingStatus;
    }

    public String getNextBillingDate () {
        return nextBillingDate;
    }

    public void setNextBillingDate (String nextBillingDate) {
        this.nextBillingDate = nextBillingDate;
    }

    public String getLastBillingDate () {
        return lastBillingDate;
    }

    public void setLastBillingDate (String lastBillingDate) {
        this.lastBillingDate = lastBillingDate;
    }

    public String getTrialEndDate () {
        return trialEndDate;
    }

    public void setTrialEndDate (String trialEndDate) {
        this.trialEndDate = trialEndDate;
    }

    public String getCreatedAt () {
        return createdAt;
    }

    public void setCreatedAt (String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt () {
        return updatedAt;
    }

    public void setUpdatedAt (String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void copyFrom (Subscription subscription) {
        Assert.notNull(subscription);

        this.id = subscription.getId();
        this.userId = subscription.getUserId();
        this.name = subscription.getName();
        this.subscriptionKey = subscription.getSubscriptionKey();
        this.status = subscription.getStatus();
        this.billingStatus = subscription.getBillingStatus();
        this.nextBillingDate = DateUtils.zonedDateTimeToISO8601(subscription.getNextBillingDate());
        this.lastBillingDate = DateUtils.zonedDateTimeToISO8601(subscription.getLastBillingDate());
        this.trialEndDate = DateUtils.zonedDateTimeToISO8601(subscription.getTrialEndDate());
        this.createdAt = DateUtils.zonedDateTimeToISO8601(subscription.getCreatedAt());
        this.updatedAt = DateUtils.zonedDateTimeToISO8601(subscription.getUpdatedAt());
    }
}
