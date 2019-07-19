package com.rms.rms.common.view_model;

import com.rms.rms.common.exception.InvalidViewModelException;
import com.rms.rms.common.util.MyDateUtil;
import org.springframework.web.util.HtmlUtils;

import java.util.Date;

/**
 * homertruong
 */

public class DiscountCodeUpdateModel {

    private String code;
    private Double discount;
    private Date endDate;
    private String note;
    private Date startDate;

    public void escapeHtml() {
        this.code = HtmlUtils.htmlEscape(this.code, "UTF-8");
        this.note = HtmlUtils.htmlEscape(this.note, "UTF-8");
    }

    public String validate() {
        String errors = "";

        // startDate
        if (startDate != null) {    // update startDate
            if (startDate.before(new Date())) {
                errors += InvalidViewModelException.DISCOUNT_CODE_START_DATE_INVALID_MESSAGE;
            }

            if (endDate != null) { // update endDate
                if (startDate.after(endDate)) {
                    errors += InvalidViewModelException.DISCOUNT_CODE_DATES_INVALID_MESSAGE;
                }
            }

            startDate = MyDateUtil.convertToUTCDate(startDate);
        }

        return errors.equals("") ? null : errors;
    }

    //
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return "DiscountCodeUpdateModel{" +
                "code='" + code + '\'' +
                ", discount=" + discount +
                ", endDate=" + endDate +
                ", note='" + note + '\'' +
                ", startDate=" + startDate +
                '}';
    }
}
