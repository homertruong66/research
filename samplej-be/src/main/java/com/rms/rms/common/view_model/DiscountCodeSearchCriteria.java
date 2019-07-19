package com.rms.rms.common.view_model;

import java.io.Serializable;
import java.util.Date;

/**
 * homertruong
 */

public class DiscountCodeSearchCriteria implements Serializable {

    private String code;
    private Date endDate;
    private Date startDate;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return "DiscountCodeSearchCriteria{" +
                "code='" + code + '\'' +
                ", endDate=" + endDate +
                ", startDate=" + startDate +
                '}';
    }
}
