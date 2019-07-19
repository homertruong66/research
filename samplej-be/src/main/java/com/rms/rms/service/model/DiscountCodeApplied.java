package com.rms.rms.service.model;

import com.rms.rms.persistence.BaseEntityExtensible;

import java.util.Date;

/**
 * homertruong
 */

public class DiscountCodeApplied extends BaseEntityExtensible {

    private String affiliateId;
    private String code;
    private Double discount;
    private Date endDate;
    private String note;
    private Date startDate;
    private String subscriberId;

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

    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    @Override
    public String toString() {
        return "DiscountCodeApplied{" +
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
