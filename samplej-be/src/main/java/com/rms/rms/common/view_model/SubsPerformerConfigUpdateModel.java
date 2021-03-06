package com.rms.rms.common.view_model;

import com.google.common.collect.Sets;
import com.rms.rms.service.model.SubsPerformerConfig;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;

public class SubsPerformerConfigUpdateModel {

    private static final Set<String> CYCLES = Sets.newHashSet(
            SubsPerformerConfig.CYCLE_WEEK,
            SubsPerformerConfig.CYCLE_MONTH,
            SubsPerformerConfig.CYCLE_YEAR
    );

    private Integer clickCount;
    private String cycle;
    private Long directNetworkRevenue;
    private Long indirectNetworkRevenue;
    private Integer newAffiliateCount;
    private Integer newCustomerCount;
    private Integer newOrderCount;
    private Long revenue;
    private Long topN;

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

    public Long getIndirectNetworkRevenue() {
        return indirectNetworkRevenue;
    }

    public void setIndirectNetworkRevenue(Long indirectNetworkRevenue) {
        this.indirectNetworkRevenue = indirectNetworkRevenue;
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

    public Long getRevenue() {
        return revenue;
    }

    public void setRevenue(Long revenue) {
        this.revenue = revenue;
    }

    public Long getTopN() {
        return topN;
    }

    public void setTopN(Long topN) {
        this.topN = topN;
    }

    public void escapeHtml() {
    }

    public String validate() {
        String errors = "";

        if( clickCount != null && clickCount.intValue() < 0 ) {
            errors += "'clickCount' can't be less than 0! && ";
        }

        if( StringUtils.isBlank(cycle) ) {
            errors += "'cycle' can't be blank! && ";
        }

        if( !CYCLES.contains(cycle) ) {
            errors += "'cycle' must be one of " + CYCLES.toString() + "! && ";
        }

        if( directNetworkRevenue != null && directNetworkRevenue.intValue() < 0 ) {
            errors += "'direct_network_revenue' can't be less than 0! && ";
        }

        if( indirectNetworkRevenue != null && indirectNetworkRevenue.intValue() < 0 ) {
            errors += "'in_direct_network_revenue' can't be less than 0! && ";
        }

        if( newAffiliateCount != null && newAffiliateCount.intValue() < 0 ) {
            errors += "'new_affiliate_count' can't be less than 0! && ";
        }

        if( newCustomerCount != null && newCustomerCount.intValue() < 0 ) {
            errors += "'new_customer_count' can't be less than 0! && ";
        }

        if( newOrderCount != null && newOrderCount.intValue() < 0 ) {
            errors += "'new_order_count' can't be less than 0! && ";
        }

        if( revenue != null && revenue.intValue() < 0 ) {
            errors += "'revenue' can't be less than 0! && ";
        }

        return errors.equals("") ? null : errors;
    }

    @Override
    public String toString() {
        return "SubsPerformerConfigUpdateModel{" +
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
