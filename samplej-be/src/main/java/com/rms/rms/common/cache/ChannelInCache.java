package com.rms.rms.common.cache;

import java.io.Serializable;

public class ChannelInCache implements Serializable {

    private static final long serialVersionUID = -8887414805197953936L;

    private String domainName;
    private String id;
    private String subscriberId;

    public String getId () {
        return id;
    }

    public void setId (String id) {
        this.id = id;
    }

    public String getDomainName () {
        return domainName;
    }

    public void setDomainName (String domainName) {
        this.domainName = domainName;
    }

    public String getSubscriberId () {
        return subscriberId;
    }

    public void setSubscriberId (String subscriberId) {
        this.subscriberId = subscriberId;
    }

    @Override
    public String toString() {
        return "ChannelInCache{" +
                "domainName='" + domainName + '\'' +
                ", id='" + id + '\'' +
                ", subscriberId='" + subscriberId + '\'' +
                '}';
    }
}
