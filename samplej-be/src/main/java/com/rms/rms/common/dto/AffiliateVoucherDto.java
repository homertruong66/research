package com.rms.rms.common.dto;

import com.rms.rms.common.BaseDto;

import java.io.Serializable;

public class AffiliateVoucherDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = 3051546725327651864L;

    private AffiliateDto affiliate;
    private String affiliateId;
    private VoucherDto voucher;
    private String voucherId;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public AffiliateDto getAffiliate() {
        return affiliate;
    }

    public void setAffiliate(AffiliateDto affiliate) {
        this.affiliate = affiliate;
    }

    public String getAffiliateId() {
        return affiliateId;
    }

    public void setAffiliateId(String affiliateId) {
        this.affiliateId = affiliateId;
    }

    public VoucherDto getVoucher() {
        return voucher;
    }

    public void setVoucher(VoucherDto voucher) {
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
        return "AffiliateVoucherDto{" +
                "affiliateId='" + affiliateId + '\'' +
                ", voucherId='" + voucherId + '\'' +
                '}';
    }
}