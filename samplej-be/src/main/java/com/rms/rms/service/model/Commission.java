package com.rms.rms.service.model;

import com.rms.rms.persistence.BaseEntityExtensible;

/**
 * homertruong
 */

public class Commission extends BaseEntityExtensible {

    public static final String TYPE_CBS  = "CBS";       // Commission By Selling
    public static final String TYPE_COAN  = "COAN";     // Commission On Affiliate Network
    public static final String TYPE_COSAL = "COSAL";    // Commission On Same Affiliate Level
    public static final String TYPE_COOV  = "COOV";     // Commission On Order Value
    public static final String TYPE_COPG  = "COPG";     // Commission On Priority Group
    public static final String TYPE_COPS  = "COPS";     // Commission On Product Set
    public static final String TYPE_COPQ  = "COPQ";     // Commission On Product Quantity
    public static final String TYPE_CDC = "CDC";        // Commission Discount
    public static final String TYPE_COASV = "COASV";    // Commission On Affiliate Sales Value

    private Affiliate affiliate;
    private String affiliateId;
    private DiscountCodeApplied discountCodeApplied;
    private String discountCodeAppliedId;
    private Double earning;      // money
    private String orderId;
    private SubsCommissionConfigApplied subsCommissionConfigApplied;
    private String subsCommissionConfigAppliedId;
    private Subscriber subscriber;
    private String subscriberId;
    private String type;
    private Double value;        // percentage

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

    public DiscountCodeApplied getDiscountCodeApplied() {
        return discountCodeApplied;
    }

    public void setDiscountCodeApplied(DiscountCodeApplied discountCodeApplied) {
        this.discountCodeApplied = discountCodeApplied;
    }

    public String getDiscountCodeAppliedId() {
        return discountCodeAppliedId;
    }

    public void setDiscountCodeAppliedId(String discountCodeAppliedId) {
        this.discountCodeAppliedId = discountCodeAppliedId;
    }

    public Double getEarning() {
        return earning;
    }

    public void setEarning(Double earning) {
        this.earning = earning;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public SubsCommissionConfigApplied getSubsCommissionConfigApplied() {
        return subsCommissionConfigApplied;
    }

    public void setSubsCommissionConfigApplied(SubsCommissionConfigApplied subsCommissionConfigApplied) {
        this.subsCommissionConfigApplied = subsCommissionConfigApplied;
    }

    public String getSubsCommissionConfigAppliedId() {
        return subsCommissionConfigAppliedId;
    }

    public void setSubsCommissionConfigAppliedId(String subsCommissionConfigAppliedId) {
        this.subsCommissionConfigAppliedId = subsCommissionConfigAppliedId;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Commission{" +
                "affiliateId='" + affiliateId + '\'' +
                ", discountCodeAppliedId='" + discountCodeAppliedId + '\'' +
                ", earning='" + earning + '\'' +
                ", orderId='" + orderId + '\'' +
                ", subsCommissionConfigAppliedId='" + subsCommissionConfigAppliedId + '\'' +
                ", subscriberId='" + subscriberId + '\'' +
                ", type='" + type + '\'' +
                ", value=" + value +
                '}';
    }
}
