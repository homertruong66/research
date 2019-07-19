package com.rms.rms.service.model;

import com.rms.rms.persistence.BaseEntityExtensible;

/**
 * homertruong
 */

public class SubsPackageConfig extends BaseEntityExtensible {

    public static final int MIN_AFFILIATE_COUNT = 100;
    public static final int MAX_AFFILIATE_COUNT = 10000;

    public static final int MIN_CHANNEL_COUNT = 2;
    public static final int MAX_CHANNEL_COUNT = 20;

    private Integer affiliateCount = 0;
    private Integer channelCount = 0;
    private Boolean hasVoucher = Boolean.FALSE;
    private Subscriber subscriber;

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

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public String toString() {
        return "SubsPackageConfig{" +
                "affiliateCount=" + affiliateCount +
                ", channelCount=" + channelCount +
                ", hasVoucher=" + hasVoucher +
                '}';
    }
}
