package com.rms.rms.common.view_model;

import java.io.Serializable;

/**
 * homertruong
 */

public class PaymentSearchCriteria implements Serializable {

    private static final long serialVersionUID = 920596926079520267L;

    private String affiliateId;
    private String status;
    private String subscriberId;

    public String getAffiliateId() {
        return affiliateId;
    }

    public void setAffiliateId(String affiliateId) {
        this.affiliateId = affiliateId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    @Override
    public String toString() {
        return "PaymentSearchCriteria{" +
                "affiliateId='" + affiliateId + '\'' +
                ", status='" + status + '\'' +
                ", subscriberId='" + subscriberId + '\'' +
                '}';
    }

}
