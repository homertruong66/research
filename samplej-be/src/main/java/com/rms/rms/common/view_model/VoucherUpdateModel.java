package com.rms.rms.common.view_model;

import com.rms.rms.common.exception.InvalidViewModelException;
import org.springframework.web.util.HtmlUtils;

import java.util.Date;

public class VoucherUpdateModel {

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

        // startDate
        if (startDate != null) {    // update startDate
            if (startDate.before(new Date())) {
                errors += InvalidViewModelException.VOUCHER_START_DATE_INVALID_MESSAGE;
            }

            if (endDate != null) { // update endDate
                if (!startDate.before(endDate)) {
                    errors += InvalidViewModelException.VOUCHER_DATES_INVALID_MESSAGE;
                }
            }
        }

        return errors.equals("") ? null : errors;
    }

    //
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
        return "VoucherUpdateModel{" +
                "benefit=" + benefit +
                ", code='" + code + '\'' +
                ", endDate=" + endDate +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", type='" + type + '\'' +
                '}';
    }
}
