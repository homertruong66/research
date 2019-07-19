package com.rms.rms.service.model;

import com.rms.rms.persistence.BaseEntityExtensible;

/**
 * homertruong
 */

public class Agent extends BaseEntityExtensible {

    private Affiliate affiliate;
    private String affiliateId;
    private Double earning;
    private String inheritor;
    private Long numberOfAffiliatesInNetwork;
    private String referrer;
    private Subscriber subscriber;
    private String subscriberId;

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

    public Double getEarning() {
        return earning;
    }

    public void setEarning(Double earning) {
        this.earning = earning;
    }

    public String getInheritor() {
        return inheritor;
    }

    public void setInheritor(String inheritor) {
        this.inheritor = inheritor;
    }

    public Long getNumberOfAffiliatesInNetwork() {
        return numberOfAffiliatesInNetwork;
    }

    public void setNumberOfAffiliatesInNetwork(Long numberOfAffiliatesInNetwork) {
        this.numberOfAffiliatesInNetwork = numberOfAffiliatesInNetwork;
    }

    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Subscriber subscriber) {
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
        return "Agent{" +
                "affiliateId='" + affiliateId + '\'' +
                ", earning=" + earning +
                ", inheritor='" + inheritor + '\'' +
                ", numberOfAffiliatesInNetwork=" + numberOfAffiliatesInNetwork +
                ", referrer='" + referrer + '\'' +
                ", subscriberId='" + subscriberId + '\'' +
                '}';
    }
}
