package com.rms.rms.service.model;

/**
 * homertruong
 */

public class Channel extends Person {

    private String domainName;
    private Subscriber subscriber;
    private String subscriberId;

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
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
        return "Channel{" +
                ", domainName='" + domainName + '\'' +
                ", subscriberId='" + subscriberId + '\'' +
                '}';
    }
}
