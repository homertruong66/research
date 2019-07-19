package com.rms.rms.common.view_model;

/**
 * homertruong
 */

public class AffiliateViewNetworkModel {

    private String subscriberId;
    private String referrer;

    public String validate() {
        return null;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    @Override
    public String toString() {
        return "AffiliateViewNetworkModel{" +
                "subscriberId='" + subscriberId + '\'' +
                ", referrer='" + referrer + '\'' +
                '}';
    }
}
