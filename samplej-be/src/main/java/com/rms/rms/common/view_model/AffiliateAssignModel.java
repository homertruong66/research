package com.rms.rms.common.view_model;

import org.apache.commons.lang3.StringUtils;

public class AffiliateAssignModel {

    private String referrer;
    private String subscriberId;

    public void escapeHtml() {
    }

    public String validate() {
        String errors = "";

        if (StringUtils.isBlank(subscriberId)) {
            errors += "'subscriberId' can't be empty! && ";
        }

        return errors.equals("") ? null : errors;
    }

    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    @Override
    public String toString() {
        return "AffiliateAssignModel{" +
                "referrer='" + referrer + '\'' +
                ", subscriberId='" + subscriberId + '\'' +
                '}';
    }
}
