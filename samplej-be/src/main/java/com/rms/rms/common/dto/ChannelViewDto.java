package com.rms.rms.common.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * homertruong
 */

public class ChannelViewDto implements Serializable {

    private static final long serialVersionUID = 2170683839848335944L;

    private Long clickCount;
    private Long customerCount;
    private Long orderCount;
    private Double orderRevenue;
    private Double approvedOrderRevenue;
    private Double nonApprovedOrderRevenue;
    private List<Map<Date, Integer>> clicksByDate;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getClickCount() {
        return clickCount;
    }

    public void setClickCount(Long clickCount) {
        this.clickCount = clickCount;
    }

    public Long getCustomerCount() {
        return customerCount;
    }

    public void setCustomerCount(Long customerCount) {
        this.customerCount = customerCount;
    }

    public Long getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Long orderCount) {
        this.orderCount = orderCount;
    }

    public Double getOrderRevenue() {
        return orderRevenue;
    }

    public void setOrderRevenue(Double orderRevenue) {
        this.orderRevenue = orderRevenue;
    }

    public Double getApprovedOrderRevenue() {
        return approvedOrderRevenue;
    }

    public void setApprovedOrderRevenue(Double approvedOrderRevenue) {
        this.approvedOrderRevenue = approvedOrderRevenue;
    }

    public Double getNonApprovedOrderRevenue() {
        return nonApprovedOrderRevenue;
    }

    public void setNonApprovedOrderRevenue(Double nonApprovedOrderRevenue) {
        this.nonApprovedOrderRevenue = nonApprovedOrderRevenue;
    }

    public List<Map<Date, Integer>> getClicksByDate() {
        return clicksByDate;
    }

    public void setClicksByDate(List<Map<Date, Integer>> clicksByDate) {
        this.clicksByDate = clicksByDate;
    }

    @Override
    public String toString() {
        return "DashboardDto{" +
                "clickCount=" + clickCount +
                ", customerCount=" + customerCount +
                ", orderCount=" + orderCount +
                ", orderRevenue=" + orderRevenue +
                ", approvedOrderRevenue=" + approvedOrderRevenue +
                ", nonApprovedOrderRevenue=" + nonApprovedOrderRevenue +
                ", clicksByDate=" + clicksByDate +
                '}';
    }
}