package com.rms.rms.common.view_model;

import com.rms.rms.common.util.MyStringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.HtmlUtils;

/**
 * homertruong
 */

public class CustomerCreateModel {

    private String address;
    private String email;
    private String firstSellerId;
    private String fullname;
    private String metadata;
    private String phone;
    private String subscriberId;

    public void escapeHtml() {
        this.address = HtmlUtils.htmlEscape(this.address, "UTF-8");
        this.fullname = HtmlUtils.htmlEscape(this.fullname, "UTF-8");
        this.metadata = HtmlUtils.htmlEscape(this.metadata, "UTF-8");
        this.phone = HtmlUtils.htmlEscape(this.phone, "UTF-8");
    }

    public String validate() {
        String errors = "";

        if (StringUtils.isBlank(email)) {
            errors += "'email' can't be empty! && ";
        }
        else if (!MyStringUtil.isEmailFormatCorrect(email)) {
            errors += "'email' has incorrect format! && ";
        }

        if (StringUtils.isBlank(fullname)) {
            errors += "'fullname' can't be empty! && ";
        }

        return errors.equals("") ? null : errors;
    }

    //

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstSellerId() {
        return firstSellerId;
    }

    public void setFirstSellerId(String firstSellerId) {
        this.firstSellerId = firstSellerId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    @Override
    public String toString() {
        return "CustomerCreateModel{" +
                "address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", firstSellerId='" + firstSellerId + '\'' +
                ", fullname='" + fullname + '\'' +
                ", metadata='" + metadata + '\'' +
                ", phone='" + phone + '\'' +
                ", subscriberId='" + subscriberId + '\'' +
                '}';
    }
}
