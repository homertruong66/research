package com.rms.rms.common.dto;

import com.rms.rms.common.BaseDto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * homertruong
 */

public class SubsCommissionConfigDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = -5258897748137303215L;

    private Double commission;
    private Set<ConditionCommissionConfigDto> conditionCommissionConfigs = new HashSet<>();
    private Date endDate;
    private Boolean isEnabled;
    private Set<LayerCommissionConfigDto> layerCommissionConfigs = new HashSet<>();
    private Integer lowerCondition;
    private String name;
    private Set<PriorityGroupDto> priorityGroups = new HashSet<>();
    private Set<ProductSetDto> productSets = new HashSet<>();
    private Date startDate;
    private String subscriberId;
    private String type;
    private Integer upperCondition;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Double getCommission () {
        return commission;
    }

    public void setCommission (Double commission) {
        this.commission = commission;
    }

    public Set<ConditionCommissionConfigDto> getConditionCommissionConfigs() {
        return conditionCommissionConfigs;
    }

    public void setConditionCommissionConfigs(Set<ConditionCommissionConfigDto> conditionCommissionConfigs) {
        this.conditionCommissionConfigs = conditionCommissionConfigs;
    }

    public Date getEndDate () {
        return endDate;
    }

    public void setEndDate (Date endDate) {
        this.endDate = endDate;
    }

    public Boolean getIsEnabled () {
        return isEnabled;
    }

    public void setIsEnabled (Boolean enabled) {
        isEnabled = enabled;
    }

    public Set<LayerCommissionConfigDto> getLayerCommissionConfigs () {
        return layerCommissionConfigs;
    }

    public void setLayerCommissionConfigs (Set<LayerCommissionConfigDto> layerCommissionConfigs) {
        this.layerCommissionConfigs = layerCommissionConfigs;
    }

    public Integer getLowerCondition () {
        return lowerCondition;
    }

    public void setLowerCondition (Integer lowerCondition) {
        this.lowerCondition = lowerCondition;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public Set<PriorityGroupDto> getPriorityGroups() {
        return priorityGroups;
    }

    public void setPriorityGroups(Set<PriorityGroupDto> priorityGroups) {
        this.priorityGroups = priorityGroups;
    }

    public Set<ProductSetDto> getProductSets() {
        return productSets;
    }

    public void setProductSets(Set<ProductSetDto> productSets) {
        this.productSets = productSets;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getSubscriberId () {
        return subscriberId;
    }

    public void setSubscriberId (String subscriberId) {
        this.subscriberId = subscriberId;
    }

    public String getType () {
        return type;
    }

    public void setType (String type) {
        this.type = type;
    }

    public Integer getUpperCondition () {
        return upperCondition;
    }

    public void setUpperCondition (Integer upperCondition) {
        this.upperCondition = upperCondition;
    }

    @Override
    public String toString () {
        return "SubsCommissionConfigDto{" +
               "commission=" + commission +
               ", endDate=" + endDate +
               ", isEnabled=" + isEnabled +
               ", lowerCondition=" + lowerCondition +
               ", name='" + name + '\'' +
               ", startDate=" + startDate +
               ", subscriberId='" + subscriberId + '\'' +
               ", type='" + type + '\'' +
               ", upperCondition=" + upperCondition +
               '}';
    }
}