package com.rms.rms.common.dto;

import com.rms.rms.common.BaseDto;

import java.io.Serializable;

/**
 * homertruong
 */

public class PerformerDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = 3149186083822357682L;

    private String affiliateId;
    private Integer clickCount;
    private String cycle;
    private Long directNetworkRevenue;
    private String firstName;
    private Long indirectNetworkRevenue;
    private String lastName;
    private Integer newAffiliateCount;
    private Integer newCustomerCount;
    private Integer newOrderCount;
    private String nickname;
    private Long revenue;
    private String subscriberDomainName;
    private String subscriberId;
    private String subscriberName;
    private Integer topN;

    public String getAffiliateId() {
        return affiliateId;
    }

    public void setAffiliateId(String affiliateId) {
        this.affiliateId = affiliateId;
    }

    public Integer getClickCount() {
        return clickCount;
    }

    public void setClickCount(Integer clickCount) {
        this.clickCount = clickCount;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public Long getDirectNetworkRevenue() {
        return directNetworkRevenue;
    }

    public void setDirectNetworkRevenue(Long directNetworkRevenue) {
        this.directNetworkRevenue = directNetworkRevenue;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Long getIndirectNetworkRevenue() {
        return indirectNetworkRevenue;
    }

    public void setIndirectNetworkRevenue(Long indirectNetworkRevenue) {
        this.indirectNetworkRevenue = indirectNetworkRevenue;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getNewAffiliateCount() {
        return newAffiliateCount;
    }

    public void setNewAffiliateCount(Integer newAffiliateCount) {
        this.newAffiliateCount = newAffiliateCount;
    }

    public Integer getNewCustomerCount() {
        return newCustomerCount;
    }

    public void setNewCustomerCount(Integer newCustomerCount) {
        this.newCustomerCount = newCustomerCount;
    }

    public Integer getNewOrderCount() {
        return newOrderCount;
    }

    public void setNewOrderCount(Integer newOrderCount) {
        this.newOrderCount = newOrderCount;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Long getRevenue() {
        return revenue;
    }

    public void setRevenue(Long revenue) {
        this.revenue = revenue;
    }

    public String getSubscriberDomainName() {
        return subscriberDomainName;
    }

    public void setSubscriberDomainName(String subscriberDomainName) {
        this.subscriberDomainName = subscriberDomainName;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    public String getSubscriberName() {
        return subscriberName;
    }

    public void setSubscriberName(String subscriberName) {
        this.subscriberName = subscriberName;
    }

    public Integer getTopN() {
        return topN;
    }

    public void setTopN(Integer topN) {
        this.topN = topN;
    }

    @Override
    public String toString() {
        return "PerformerDto{" +
                "affiliateId='" + affiliateId + '\'' +
                ", clickCount=" + clickCount +
                ", cycle='" + cycle + '\'' +
                ", directNetworkRevenue=" + directNetworkRevenue +
                ", firstName='" + firstName + '\'' +
                ", indirectNetworkRevenue=" + indirectNetworkRevenue +
                ", lastName='" + lastName + '\'' +
                ", newAffiliateCount=" + newAffiliateCount +
                ", newCustomerCount=" + newCustomerCount +
                ", newOrderCount=" + newOrderCount +
                ", nickname='" + nickname + '\'' +
                ", revenue=" + revenue +
                ", subscriberDomainName='" + subscriberDomainName + '\'' +
                ", subscriberId='" + subscriberId + '\'' +
                ", subscriberName='" + subscriberName + '\'' +
                ", topN=" + topN +
                '}';
    }
}
