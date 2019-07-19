package com.rms.rms.common.view_model;

import com.rms.rms.common.util.MyDateUtil;

import java.util.Date;

/**
 * homertruong
 */

public class ConditionCommissionConfigUpdateModel {

    private Double commission;
    private Date endDate;
    private Integer lowerCondition;
    private String productId;
    private Date startDate;
    private Integer upperCondition;

    public void escapeHtml() {
    }

    public String validate() {
        String errors = "";

        if (commission != null) {
            if (commission > 1) {
                errors += "'commission' can't be > 1 ! && ";
            }

            if (commission < 0) {
                errors += "'commission' can't be < 0 ! && ";
            }
        }

        if (startDate != null && endDate != null && startDate.after(endDate)) {
            errors += "start_date [" + MyDateUtil.getYYMMDDString(startDate) + "] can't be after " +
                      "end_date [" + MyDateUtil.getYYMMDDString(endDate) + "]! && ";
        }

        if (lowerCondition != null && upperCondition != null && lowerCondition.intValue() > upperCondition.intValue()) {
            errors += "'lower_condition' can't be greater than 'upper_condition'! && ";
        }

        return errors.equals("") ? null : errors;
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

    public Integer getUpperCondition() {
        return upperCondition;
    }

    public void setUpperCondition(Integer upperCondition) {
        this.upperCondition = upperCondition;
    }

    @Override
    public String toString() {
        return "ConditionCommissionConfigUpdateModel{" +
                "commission=" + commission +
                ", endDate=" + endDate +
                ", lowerCondition=" + lowerCondition +
                ", productId='" + productId + '\'' +
                ", startDate=" + startDate +
                ", upperCondition=" + upperCondition +
                '}';
    }
}
