package com.rms.rms.common.view_model;

public class SubsPackageConfigUpdateModel {

    private Integer affiliateCount;
    private Integer channelCount;
    private Boolean hasVoucher;

    public void escapeHtml() {
    }

    public String validate() {
        String errors = "";

        if (affiliateCount != null && affiliateCount.intValue() < 0) {
            errors += "'affiliate_count' can't be less than 0! && ";
        }

        if (channelCount != null && channelCount.intValue() < 0) {
            errors += "'channel_count' can't be less than 0! && ";
        }

        return errors.equals("") ? null : errors;
    }

    public Integer getAffiliateCount() {
        return affiliateCount;
    }

    public void setAffiliateCount(Integer affiliateCount) {
        this.affiliateCount = affiliateCount;
    }

    public Integer getChannelCount() {
        return channelCount;
    }

    public void setChannelCount(Integer channelCount) {
        this.channelCount = channelCount;
    }

    public Boolean getHasVoucher() {
        return hasVoucher;
    }

    public void setHasVoucher(Boolean hasVoucher) {
        this.hasVoucher = hasVoucher;
    }

    @Override
    public String toString() {
        return "SubsPackageConfigUpdateModel{" +
                "affiliateCount=" + affiliateCount +
                ", channelCount=" + channelCount +
                ", hasVoucher=" + hasVoucher +
                '}';
    }
}
