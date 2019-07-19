package com.rms.rms.service.model;

import com.rms.rms.persistence.BaseEntityExtensible;

/**
 * homertruong
 */

public class SubsPackageStatus extends BaseEntityExtensible {

    private Integer affiliateCount = 0;
    private Integer channelCount = 0;
    private Integer layerCount = 0;
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

    public Integer getLayerCount() {
        return layerCount;
    }

    public void setLayerCount(Integer layerCount) {
        this.layerCount = layerCount;
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public String toString() {
        return "SubsPackageStatus{" +
                "affiliateCount=" + affiliateCount +
                ", channelCount=" + channelCount +
                ", layerCount=" + layerCount +
                '}';
    }
}
