package com.rms.rms.common.dto;

import com.rms.rms.common.BaseDto;

import java.io.Serializable;

public class SubsPackageConfigDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = 2147576599727924513L;

    private Integer affiliateCount;
    private Integer channelCount;
    private Boolean hasVoucher;
    private SubscriberDto subscriber;

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public SubscriberDto getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(SubscriberDto subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public String toString() {
        return "SubsPackageConfigDto{" +
                "affiliateCount=" + affiliateCount +
                ", channelCount=" + channelCount +
                ", hasVoucher=" + hasVoucher +
                '}';
    }
}