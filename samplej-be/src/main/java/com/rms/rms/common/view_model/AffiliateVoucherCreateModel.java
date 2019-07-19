package com.rms.rms.common.view_model;

import org.springframework.util.CollectionUtils;

import java.util.List;

public class AffiliateVoucherCreateModel {

    private List<String> affiliateIds;
    private List<String> voucherIds;

    public void escapeHtml() {}

    public String validate() {
        String errors = "";

        if (CollectionUtils.isEmpty(affiliateIds)) {
            errors += "'affiliateIds' can't be empty! && ";
        }

        if (CollectionUtils.isEmpty(voucherIds)) {
            errors += "'voucherIds' can't be empty!";
        }

        return errors.equals("") ? null : errors;
    }

    public List<String> getAffiliateIds() {
        return affiliateIds;
    }

    public void setAffiliateIds(List<String> affiliateIds) {
        this.affiliateIds = affiliateIds;
    }

    public List<String> getVoucherIds() {
        return voucherIds;
    }

    public void setVoucherIds(List<String> voucherIds) {
        this.voucherIds = voucherIds;
    }

    @Override
    public String toString() {
        return "AffiliateVoucherCreateModel{" +
                "affiliateIds=" + affiliateIds +
                ", voucherIds=" + voucherIds +
                '}';
    }
}
