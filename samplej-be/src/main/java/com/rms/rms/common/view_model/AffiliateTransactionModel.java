package com.rms.rms.common.view_model;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;

public class AffiliateTransactionModel {

    private String affiliateId;
    private Date fromDate;
    private Date toDate;
    private String subscriberId;

    public void escapeHtml() {}

    public String validate() {
        String errors = "";

        if (StringUtils.isBlank(subscriberId)) {
            errors += "'subscriber_id' can't be empty!";
        }

        return errors.equals("") ? null : errors;
    }

    //
    public String getAffiliateId() {
        return affiliateId;
    }

    public void setAffiliateId(String affiliateId) {
        this.affiliateId = affiliateId;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    @Override
    public String toString() {
        return "AffiliateTransactionModel{" +
                "affiliateId='" + affiliateId + '\'' +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", subscriberId='" + subscriberId + '\'' +
                '}';
    }
}
