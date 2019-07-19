package com.rms.rms.common.view_model;

public class AgentSearchCriteria {

    private String affiliateId;
    private String subscriberId;

    public String getAffiliateId() {
        return affiliateId;
    }

    public void setAffiliateId(String affiliateId) {
        this.affiliateId = affiliateId;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    @Override
    public String toString() {
        return "AgentSearchCriteria{" +
                "affiliateId='" + affiliateId + '\'' +
                ", subscriberId='" + subscriberId + '\'' +
                '}';
    }
}
