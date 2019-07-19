package com.rms.rms.common.view_model;

/**
 * homertruong
 */

public class SubsAdminSearchCriteria extends PersonSearchCriteria {

    private String subscriberId;

    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    @Override
    public String toString() {
        return "SubsAdminSearchCriteria{" +
                "subscriberId='" + subscriberId + '\'' +
                '}';
    }
}
