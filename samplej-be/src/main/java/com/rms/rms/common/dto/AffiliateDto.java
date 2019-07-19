package com.rms.rms.common.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * homertruong
 */

public class AffiliateDto extends PersonDto implements Serializable {

    private static final long serialVersionUID = 5906435116261639007L;

    public static final String CONFIRM_MESSAGE_FOR_CHANNEL = "Affiliate not belongs to this Subscriber. Register this Subscriber?";
    public static final String CONFIRM_MESSAGE_FOR_THE_OTHERS = "Affiliate already assigned to other Subscribers. Assign the Affiliate to this Subscriber?";

    private String activationCode;
    private String confirmMessage;
    private Boolean isGetResponseSuccess;
    private Boolean isPhoneVerified;
    private String metadata;
    private String nickname;
    private Long numberOfAffiliatesInNetwork;
    private String referrer;
    private Set<SubscriberDto> subscribers = new HashSet<>();
    private String subscriberId;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public String getConfirmMessage() {
        return confirmMessage;
    }

    public void setConfirmMessage(String confirmMessage) {
        this.confirmMessage = confirmMessage;
    }

    public Boolean getIsGetResponseSuccess() {
        return isGetResponseSuccess;
    }

    public void setIsGetResponseSuccess(Boolean getResponseSuccess) {
        isGetResponseSuccess = getResponseSuccess;
    }

    public Boolean getIsPhoneVerified() {
        return isPhoneVerified;
    }

    public void setIsPhoneVerified(Boolean isPhoneVerified) {
        this.isPhoneVerified = isPhoneVerified;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public Set<SubscriberDto> getSubscribers () {
        return subscribers;
    }

    public void setSubscribers (Set<SubscriberDto> subscribers) {
        this.subscribers = subscribers;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    @Override
    public String toString() {
        return "AffiliateDto{" +
                "activationCode='" + activationCode + '\'' +
                ", confirmMessage='" + confirmMessage + '\'' +
                ", isGetResponseSuccess=" + isGetResponseSuccess +
                ", isPhoneVerified=" + isPhoneVerified +
                ", metadata='" + metadata + '\'' +
                ", nickname='" + nickname + '\'' +
                ", numberOfAffiliatesInNetwork='" + numberOfAffiliatesInNetwork + '\'' +
                ", referrer='" + referrer + '\'' +
                ", subscriberId='" + subscriberId + '\'' +
                '}';
    }
}