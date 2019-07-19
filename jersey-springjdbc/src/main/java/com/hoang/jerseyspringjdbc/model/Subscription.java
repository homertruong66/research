package com.hoang.jerseyspringjdbc.model;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class Subscription implements Serializable {

    private static final long serialVersionUID = 502370265642212359L;

    private int           id;
    private String        name;
    private String        hostname;
    private int           userId;
    private String        subscriptionKey;
    private String        status;
    private String        billingStatus;
    private ZonedDateTime nextBillingDate;
    private ZonedDateTime lastBillingDate;
    private ZonedDateTime trialEndDate;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

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

    public String getHostname () {
        return hostname;
    }

    public void setHostname (String hostname) {
        this.hostname = hostname;
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

    public ZonedDateTime getNextBillingDate () {
        return nextBillingDate;
    }

    public void setNextBillingDate (ZonedDateTime nextBillingDate) {
        this.nextBillingDate = nextBillingDate;
    }

    public ZonedDateTime getLastBillingDate () {
        return lastBillingDate;
    }

    public void setLastBillingDate (ZonedDateTime lastBillingDate) {
        this.lastBillingDate = lastBillingDate;
    }

    public ZonedDateTime getTrialEndDate () {
        return trialEndDate;
    }

    public void setTrialEndDate (ZonedDateTime trialEndDate) {
        this.trialEndDate = trialEndDate;
    }

    public ZonedDateTime getCreatedAt () {
        return createdAt;
    }

    public void setCreatedAt (ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt () {
        return updatedAt;
    }

    public void setUpdatedAt (ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
