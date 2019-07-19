package com.rms.rms.common.dto;

import com.rms.rms.common.BaseDto;

import java.io.Serializable;
import java.util.Date;

/**
 * homertruong
 */

public class DiscountCodeDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = 8162341568021694096L;

    private String affiliateId;
    private String code;
    private Double discount;
    private Date endDate;
    private String note;
    private Date startDate;
    private SubscriberDto subscriber;
    private String subscriberId;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getAffiliateId() {
        return affiliateId;
    }

    public void setAffiliateId(String affiliateId) {
        this.affiliateId = affiliateId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public SubscriberDto getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(SubscriberDto subscriber) {
        this.subscriber = subscriber;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    @Override
    public String toString() {
        return "DiscountCodeDto{" +
                "affiliateId='" + affiliateId + '\'' +
                ", code='" + code + '\'' +
                ", discount=" + discount +
                ", endDate=" + endDate +
                ", note='" + note + '\'' +
                ", startDate=" + startDate +
                ", subscriberId=" + subscriberId +
                '}';
    }
}