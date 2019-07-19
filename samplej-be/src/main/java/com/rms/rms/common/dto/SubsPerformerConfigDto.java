package com.rms.rms.common.dto;

import com.rms.rms.common.BaseDto;

import java.io.Serializable;

public class SubsPerformerConfigDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = 6168294535656904715L;

    private Integer clickCount;
    private String cycle;
    private Long directNetworkRevenue;
    private Long indirectNetworkRevenue;
    private Integer newAffiliateCount;
    private Integer newCustomerCount;
    private Integer newOrderCount;
    private Long revenue;
    private Integer topN;
    private SubscriberDto subscriber;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getClickCount () {
        return clickCount;
    }

    public void setClickCount (Integer clickCount) {
        this.clickCount = clickCount;
    }

    public String getCycle () {
        return cycle;
    }

    public void setCycle (String cycle) {
        this.cycle = cycle;
    }

    public Long getDirectNetworkRevenue () {
        return directNetworkRevenue;
    }

    public void setDirectNetworkRevenue (Long directNetworkRevenue) {
        this.directNetworkRevenue = directNetworkRevenue;
    }

    public Long getIndirectNetworkRevenue () {
        return indirectNetworkRevenue;
    }

    public void setIndirectNetworkRevenue (Long indirectNetworkRevenue) {
        this.indirectNetworkRevenue = indirectNetworkRevenue;
    }

    public Integer getNewAffiliateCount () {
        return newAffiliateCount;
    }

    public void setNewAffiliateCount (Integer newAffiliateCount) {
        this.newAffiliateCount = newAffiliateCount;
    }

    public Integer getNewCustomerCount () {
        return newCustomerCount;
    }

    public void setNewCustomerCount (Integer newCustomerCount) {
        this.newCustomerCount = newCustomerCount;
    }

    public Integer getNewOrderCount () {
        return newOrderCount;
    }

    public void setNewOrderCount (Integer newOrderCount) {
        this.newOrderCount = newOrderCount;
    }

    public Long getRevenue () {
        return revenue;
    }

    public void setRevenue (Long revenue) {
        this.revenue = revenue;
    }

    public Integer getTopN() {
        return topN;
    }

    public void setTopN(Integer topN) {
        this.topN = topN;
    }

    public SubscriberDto getSubscriber () {
        return subscriber;
    }

    public void setSubscriber (SubscriberDto subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public String toString() {
        return "SubsPerformerConfigDto{" +
                "clickCount=" + clickCount +
                ", cycle='" + cycle + '\'' +
                ", directNetworkRevenue=" + directNetworkRevenue +
                ", indirectNetworkRevenue=" + indirectNetworkRevenue +
                ", newAffiliateCount=" + newAffiliateCount +
                ", newCustomerCount=" + newCustomerCount +
                ", newOrderCount=" + newOrderCount +
                ", revenue=" + revenue +
                ", topN=" + topN +
                '}';
    }
}
