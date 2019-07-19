package com.rms.rms.common.dto;

import com.rms.rms.common.BaseDto;

import java.io.Serializable;

public class AgentDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = 8728021428129625896L;

    private AffiliateDto affiliate;
    private String affiliateId;
    private Double earning;
    private String inheritor;
    private Long numberOfAffiliatesInNetwork;
    private String referrer;
    private SubscriberDto subscriber;
    private String subscriberId;

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

    public SubscriberDto getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(SubscriberDto subscriber) {
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
        return "AgentDto{" +
                "affiliateId='" + affiliateId + '\'' +
                ", earning=" + earning +
                ", inheritor='" + inheritor + '\'' +
                ", numberOfAffiliatesInNetwork=" + numberOfAffiliatesInNetwork +
                ", referrer='" + referrer + '\'' +
                ", subscriberId='" + subscriberId + '\'' +
                '}';
    }
}