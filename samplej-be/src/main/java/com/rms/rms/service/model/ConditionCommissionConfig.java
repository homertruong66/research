package com.rms.rms.service.model;

import com.rms.rms.persistence.BaseEntityExtensible;

import java.util.Date;

public class ConditionCommissionConfig extends BaseEntityExtensible {

    private Double  commission;
    private Date    endDate;
    private Integer lowerCondition;
    private String productId;
    private Date    startDate;
    private SubsCommissionConfig subsCommissionConfig;
    private String subsCommissionConfigId;
    private Integer upperCondition;

    public Double getCommission() {
        return commission;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getLowerCondition() {
        return lowerCondition;
    }

    public void setLowerCondition(Integer lowerCondition) {
        this.lowerCondition = lowerCondition;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public SubsCommissionConfig getSubsCommissionConfig() {
        return subsCommissionConfig;
    }

    public void setSubsCommissionConfig(SubsCommissionConfig subsCommissionConfig) {
        this.subsCommissionConfig = subsCommissionConfig;
    }

    public String getSubsCommissionConfigId() {
        return subsCommissionConfigId;
    }

    public void setSubsCommissionConfigId(String subsCommissionConfigId) {
        this.subsCommissionConfigId = subsCommissionConfigId;
    }

    public Integer getUpperCondition() {
        return upperCondition;
    }

    public void setUpperCondition(Integer upperCondition) {
        this.upperCondition = upperCondition;
    }

    @Override
    public String toString() {
        return "ConditionCommissionConfig{" +
                "commission=" + commission +
                ", endDate=" + endDate +
                ", lowerCondition=" + lowerCondition +
                ", productId='" + productId + '\'' +
                ", startDate=" + startDate +
                ", subsCommissionConfigId='" + subsCommissionConfigId + '\'' +
                ", upperCondition=" + upperCondition +
                '}';
    }
}
