package com.rms.rms.common.dto;

import com.rms.rms.common.BaseDto;

import java.io.Serializable;
import java.util.Date;

/**
 * homertruong
 */

public class ConditionCommissionConfigDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = 4145552683819984101L;

    private Double commission;
    private Date endDate;
    private Integer lowerCondition;
    private ProductDto product;
    private String productId;
    private Date startDate;
    private String subsCommissionConfigId;
    private Integer upperCondition;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

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

    public ProductDto getProduct() {
        return product;
    }

    public void setProduct(ProductDto product) {
        this.product = product;
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
        return "ConditionCommissionConfigDto{" +
                "commission=" + commission +
                ", endDate=" + endDate +
                ", lowerCondition=" + lowerCondition +
                ", product=" + product +
                ", productId='" + productId + '\'' +
                ", startDate=" + startDate +
                ", subsCommissionConfigId='" + subsCommissionConfigId + '\'' +
                ", upperCondition=" + upperCondition +
                '}';
    }
}