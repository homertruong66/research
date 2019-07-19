package com.rms.rms.service.model;

import com.rms.rms.persistence.BaseEntityExtensible;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class PriorityGroup extends BaseEntityExtensible {

    private Set<PriorityGroupAffiliate> affiliates = new HashSet<>();
    private Double          commission;
    private String         description;
    private Date           endDate;
    private String name;
    private Date           startDate;
    private SubsCommissionConfig subsCommissionConfig;
    private String subsCommissionConfigId;

    public Set<PriorityGroupAffiliate> getAffiliates () {
        return affiliates;
    }

    public void setAffiliates (Set<PriorityGroupAffiliate> affiliates) {
        this.affiliates = affiliates;
    }

    public Double getCommission() {
        return commission;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    public String getDescription () {
        return description;
    }

    public void setDescription (String description) {
        this.description = description;
    }

    public Date getEndDate () {
        return endDate;
    }

    public void setEndDate (Date endDate) {
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate () {
        return startDate;
    }

    public void setStartDate (Date startDate) {
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

    @Override
    public String toString () {
        return "PriorityGroup{" +
               ", commission=" + commission +
               ", description='" + description + '\'' +
               ", endDate=" + endDate +
               ", name='" + name + '\'' +
               ", startDate=" + startDate +
               ", subsCommissionConfigId='" + subsCommissionConfigId + '\'' +
               '}';
    }
}
