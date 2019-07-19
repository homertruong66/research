package com.rms.rms.common.view_model;

import com.rms.rms.common.exception.InvalidViewModelException;
import com.rms.rms.common.util.MyDateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.HtmlUtils;

import java.util.Date;

/**
 * homertruong
 */

public class DiscountCodeCreateModel {

    private String code;
    private Double discount;
    private Date endDate;
    private String note;
    private Date startDate;
    private String subscriberId;

    public void escapeHtml() {
        this.code = HtmlUtils.htmlEscape(this.code, "UTF-8");
        this.note = HtmlUtils.htmlEscape(this.note, "UTF-8");
    }

    public String validate() {
        String errors = "";

        if (StringUtils.isBlank(subscriberId)) {
            errors += "'subscriber_id' can't be empty! && ";
        }

        if (StringUtils.isBlank(code)) {
            errors += "'code' can't be empty! && ";
        }

        if (discount == null) {
            errors += "'discount' can't be null! &&";
        }

        // startDate
        if (startDate == null) {
            errors += "'start_date' can't be null! &&";
        }

        // endDate
        if (endDate != null) {
            if (startDate != null && startDate.after(endDate)) {
                errors += InvalidViewModelException.DISCOUNT_CODE_DATES_INVALID_MESSAGE;
            }
            endDate = MyDateUtil.convertToUTCDate(endDate);
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

    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    @Override
    public String toString() {
        return "DiscountCodeCreateModel{" +
                "code='" + code + '\'' +
                ", discount=" + discount +
                ", endDate=" + endDate +
                ", note='" + note + '\'' +
                ", startDate=" + startDate +
                ", subscriberId=" + subscriberId +
                '}';
    }
}
