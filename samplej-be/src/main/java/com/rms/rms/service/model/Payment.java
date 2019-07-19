package com.rms.rms.service.model;

import com.rms.rms.persistence.BaseEntityExtensible;

import java.util.HashMap;
import java.util.Map;

/**
 * homertruong
 */

public class Payment extends BaseEntityExtensible {

    public static final String STATUS_NEW = "NEW";
    public static final String STATUS_APPROVED = "APPROVED";
    public static final String STATUS_REJECTED = "REJECTED";

    public static final String ACTION_APPROVE = "APPROVE";
    public static final String ACTION_REJECT = "REJECT";

    public static final Map<String, String> Action2StatusMap = new HashMap<>();
    public static final Map<String, String> StatusFlow = new HashMap<>();

    static {
        Action2StatusMap.put(ACTION_APPROVE, STATUS_APPROVED);
        Action2StatusMap.put(ACTION_REJECT, STATUS_REJECTED);

        StatusFlow.put(STATUS_NEW, String.join(" ", STATUS_APPROVED, STATUS_REJECTED));
        StatusFlow.put(STATUS_REJECTED, String.join(" ", STATUS_APPROVED));
    }

    private Affiliate affiliate;
    private String affiliateId;
    private String reason;
    private Subscriber subscriber;
    private String subscriberId;
    private String status;
    private Double value;

    public Affiliate getAffiliate() {
        return affiliate;
    }

    public void setAffiliate(Affiliate affiliate) {
        this.affiliate = affiliate;
    }

    public String getAffiliateId() {
        return affiliateId;
    }

    public void setAffiliateId(String affiliateId) {
        this.affiliateId = affiliateId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String toString() {
        return "Payment{" +
                ", affiliateId='" + affiliateId + '\'' +
                ", reason='" + reason + '\'' +
                ", subscriberId='" + subscriberId + '\'' +
                ", status='" + status + '\'' +
                ", value=" + value +
                '}';
    }
}
