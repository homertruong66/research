package com.rms.rms.common.view_model;

import com.rms.rms.common.exception.InvalidViewModelException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.HtmlUtils;

import java.util.Date;

public class VoucherCreateModel {

    private Double benefit;
    private String code;
    private Date endDate;
    private String name;
    private Date startDate;
    private String type;

    public void escapeHtml() {
        this.code = HtmlUtils.htmlEscape(this.code, "UTF-8");
        this.name = HtmlUtils.htmlEscape(this.name, "UTF-8");
        this.type = HtmlUtils.htmlEscape(this.type, "UTF-8");
    }

    public String validate() {
        String errors = "";

        if (benefit == null) {
            errors += "'benefit' can't be empty! && ";
        }
        else if (benefit.compareTo(0.0) <= 0) {
            errors += "'benefit' can't <= 0! && ";
        }

        if (StringUtils.isBlank(code)) {
            errors += "'code' can't be empty! && ";
        }

        if (StringUtils.isBlank(name)) {
            errors += "'name' can't be empty! && ";
        }

        if (StringUtils.isBlank(type)) {
            errors += "'type' can't be empty! && ";
        }

        // startDate
        if (startDate == null) {
            errors += "'start_date' can't be empty! && ";
        }

        // endDate
        if (endDate != null) {
            if (startDate != null && !startDate.before(endDate)) {
                errors += InvalidViewModelException.VOUCHER_DATES_INVALID_MESSAGE;
            }
        }

        return errors.equals("") ? null : errors;
    }

    public Double getBenefit() {
        return benefit;
    }

    public void setBenefit(Double benefit) {
        this.benefit = benefit;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return "VoucherCreateModel{" +
                "benefit=" + benefit +
                ", code='" + code + '\'' +
                ", endDate=" + endDate +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", type='" + type + '\'' +
                '}';
    }
}
