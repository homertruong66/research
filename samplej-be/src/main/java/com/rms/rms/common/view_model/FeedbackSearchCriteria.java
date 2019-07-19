package com.rms.rms.common.view_model;

public class FeedbackSearchCriteria {

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "FeedbackSearchCriteria{" +
                "status='" + status + '\'' +
                '}';
    }
}
