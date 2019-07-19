package com.rms.rms.common.view_model;

import java.io.Serializable;
import java.util.Date;

/**
 * homertruong
 */

public class SubsCommissionConfigSearchCriteria implements Serializable {

    private Date endDate;
    private Date startDate;
    private String type;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "SubsCommissionConfigSearchCriteria{" +
                "endDate=" + endDate +
                ", startDate=" + startDate +
                ", type='" + type + '\'' +
                '}';
    }
}
