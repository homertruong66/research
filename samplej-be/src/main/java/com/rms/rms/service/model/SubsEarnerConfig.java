package com.rms.rms.service.model;

import com.rms.rms.persistence.BaseEntityExtensible;

public class SubsEarnerConfig extends BaseEntityExtensible {

    public static final int MAX_CLICK_COUNT = 100000;
    public static final int MIN_CLICK_COUNT = 0;

    public static final int MAX_NEW_AFFILIATE_COUNT = 1000;
    public static final int MIN_NEW_AFFILIATE_COUNT = 0;

    public static final int MIN_DIRECT_NETWORK_REVENUE = 0;
    public static final Long MAX_DIRECT_NETWORK_REVENUE = 10000000000L;

    public static final int MIN_INDIRECT_NETWORK_REVENUE = 0;
    public static final Long MAX_INDIRECT_NETWORK_REVENUE = 100000000000L;

    public static final int MIN_NEW_CUSTOMER_COUNT = 0;
    public static final int MAX_NEW_CUSTOMER_COUNT = 10000;

    public static final int MIN_NEW_ORDER_COUNT = 0;
    public static final int MAX_NEW_ORDER_COUNT = 30000;

    public static final int MIN_REVENUE = 0;
    public static final Long MAX_REVENUE = 10000000000L;

    public static final String CYCLE_MONTH = "CYCLE_MONTH";
    public static final String CYCLE_WEEK = "CYCLE_WEEK";
    public static final String CYCLE_YEAR = "CYCLE_YEAR";

    private Integer clickCount = 300; // default is 300
    private String cycle = CYCLE_MONTH; // default is CYCLE_MONTH
    private Long directNetworkRevenue = 30000000L; // default is 30,000,000
    private Long indirectNetworkRevenue = 50000000L; // default is 50,000,000
    private Integer newAffiliateCount = 5; // default is 5
    private Integer newCustomerCount = 5; // default is 5
    private Integer newOrderCount = 10; // default is 10
    private Long revenue = 10000000L; // default is 10,000,000
    private Subscriber subscriber;

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

    public Subscriber getSubscriber () {
        return subscriber;
    }

    public void setSubscriber (Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public String toString () {
        return "SubsEarnerConfig{" +
                "clickCount=" + clickCount +
                ", cycle='" + cycle + '\'' +
                ", directNetworkRevenue=" + directNetworkRevenue +
                ", indirectNetworkRevenue=" + indirectNetworkRevenue +
                ", newAffiliateCount=" + newAffiliateCount +
                ", newCustomerCount=" + newCustomerCount +
                ", newOrderCount=" + newOrderCount +
                ", revenue=" + revenue +
                '}';
    }
}
