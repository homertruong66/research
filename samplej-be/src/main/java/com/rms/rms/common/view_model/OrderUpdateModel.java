package com.rms.rms.common.view_model;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.HtmlUtils;

/**
 * homertruong
 */

public class OrderUpdateModel {

    private String note;
    private String number;

    public void escapeHtml() {
        this.note = HtmlUtils.htmlEscape(this.note, "UTF-8");
        this.number = HtmlUtils.htmlEscape(this.number, "UTF-8");
    }

    public String validate() {
        String errors = "";

        if (!StringUtils.isBlank(number) && StringUtils.containsWhitespace(number)) {
            errors += "'number' can't contain whitespace! && ";
        }

        return errors.equals("") ? null : errors;
    }

    //
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "OrderUpdateModel{" +
                "note='" + note + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
