package com.rms.rms.common.view_model;

import java.io.Serializable;

/**
 * homertruong
 */

public class CommissionSearchCriteria implements Serializable {

    private static final long serialVersionUID = 898304405192714886L;

    private String affiliateId;
    private String subscriberId;
    private String type;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "CommissionSearchCriteria{" +
                "affiliateId='" + affiliateId + '\'' +
                ", subscriberId='" + subscriberId + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

}
