package com.rms.rms.common.view_model;

public class BillSearchCriteria {

    private String subscriberId;

    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    @Override
    public String toString() {
        return "BillSearchCriteria{" +
                "subscriberId='" + subscriberId + '\'' +
                '}';
    }
}