package com.rms.rms.common.view_model;

import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.util.MyDateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.HtmlUtils;

import java.util.Date;

public class ProductSetCreateModel {

    private Double commission;
    private String description;
    private Date   endDate;
    private String name;
    private Date   startDate;

    public void escapeHtml() {
        this.description = HtmlUtils.htmlEscape(this.description, "UTF-8");
        this.name = HtmlUtils.htmlEscape(this.name, "UTF-8");
    }

    public String validate () {
        String errors = "";

        if ( commission == null ) {
            errors += "'commission' can't be empty! && ";
        }
        else {
            if (commission > 1 ) {
                errors += "'commission' can't be > 1 ! && ";
            }

            if ( commission < 0 ) {
                errors += "'commission' can't be < 0 ! &&";
            }
        }

        if ( StringUtils.isBlank(description) ) {
            errors += "'description' can't be empty! &&";
        }

        if ( StringUtils.isBlank(name) ) {
            errors += "'name' can't be empty! &&";
        }

        if ( this.startDate != null && this.endDate != null && this.startDate.after(this.endDate) ) {
            errors += String.format(
                    BusinessException.PRODUCT_SET_START_DATE_VS_END_DATE_MESSAGE,
                    MyDateUtil.getYYMMDDString(startDate),
                    MyDateUtil.getYYMMDDString(endDate)
            );
        }

        return errors.equals("") ? null : errors;
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

    @Override
    public String toString() {
        return "ProductSetCreateModel{" +
                "commission=" + commission +
                ", description='" + description + '\'' +
                ", endDate=" + endDate +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                '}';
    }
}