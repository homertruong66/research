package com.rms.rms.common.dto;

import com.rms.rms.common.BaseDto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class PriorityGroupDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = 522444262572321335L;

    private           Set<AffiliateDto> affiliates = new HashSet<>();
    private           Double             commission;
    private           String            description;
    private           Date              endDate;
    private transient String            errors;
    private           String name;
    private           Date              startDate;
    private           String subsCommissionConfigId;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Set<AffiliateDto> getAffiliates () {
        return affiliates;
    }

    public void setAffiliates (Set<AffiliateDto> affiliates) {
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

    public String getErrors () {
        return errors;
    }

    public void setErrors (String errors) {
        this.errors = errors;
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

    public String getSubsCommissionConfigId() {
        return subsCommissionConfigId;
    }

    public void setSubsCommissionConfigId(String subsCommissionConfigId) {
        this.subsCommissionConfigId = subsCommissionConfigId;
    }

    @Override
    public String toString () {
        return "PriorityGroupDto{" +
               "affiliates=" + affiliates +
               ", commission=" + commission +
               ", description='" + description + '\'' +
               ", endDate=" + endDate +
               ", errors=" + errors +
               ", name='" + name + '\'' +
               ", startDate=" + startDate +
               ", subsCommissionConfigId='" + subsCommissionConfigId + '\'' +
               '}';
    }
}