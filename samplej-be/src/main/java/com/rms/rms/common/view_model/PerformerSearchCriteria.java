package com.rms.rms.common.view_model;

import java.io.Serializable;

public class PerformerSearchCriteria implements Serializable {

    private String subscriberId;

    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    @Override
    public String toString() {
        return "PerformerSearchCriteria{" +
                "subscriberId='" + subscriberId + '\'' +
                '}';
    }
}
