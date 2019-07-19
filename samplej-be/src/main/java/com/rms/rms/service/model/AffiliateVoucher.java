package com.rms.rms.service.model;

import com.rms.rms.persistence.BaseEntityExtensible;

public class AffiliateVoucher extends BaseEntityExtensible {

    private static final long serialVersionUID = 8457338273441529068L;

    private Affiliate affiliate;
    private String affiliateId;
    private Voucher voucher;
    private String voucherId;

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

    public Voucher getVoucher() {
        return voucher;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    public String getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(String voucherId) {
        this.voucherId = voucherId;
    }

    @Override
    public String toString() {
        return "AffiliateVoucher{" +
                "affiliateId='" + affiliateId + '\'' +
                ", voucherId='" + voucherId + '\'' +
                '}';
    }
}
