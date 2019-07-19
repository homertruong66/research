package com.rms.rms.common.dto;

import com.rms.rms.common.BaseDto;

import java.io.Serializable;

/**
 * homertruong
 */

public class CustomerDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = -4015546751496698012L;

    private String address;
    private String email;
    private String firstSellerId;
    private String fullname;
    private String metadata;
    private String phone;
    private SubscriberDto subscriber;
    private String subscriberId;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
    
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstSellerId() {
        return firstSellerId;
    }

    public void setFirstSellerId(String firstSellerId) {
        this.firstSellerId = firstSellerId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
        return "CustomerDto{" +
                "address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", firstSellerId='" + firstSellerId + '\'' +
                ", fullname='" + fullname + '\'' +
                ", metadata='" + metadata + '\'' +
                ", phone='" + phone + '\'' +
                ", subscriberId='" + subscriberId + '\'' +
                '}';
    }
}