package com.rms.rms.common.view_model;

import com.rms.rms.common.util.MyStringUtil;
import org.springframework.web.util.HtmlUtils;

/**
 * homertruong
 */

public class SubscriberUpdateModel {

    private String address;
    private String companyName;
    private String district;
    private String domainName;
    private String email;
    private String mobilePhone;
    private String phone;
    private String provinceId;
    private String website;

    public void escapeHtml() {
        this.address = HtmlUtils.htmlEscape(this.address, "UTF-8");
        this.companyName = HtmlUtils.htmlEscape(this.companyName, "UTF-8");
        this.district = HtmlUtils.htmlEscape(this.district, "UTF-8");
        this.domainName = HtmlUtils.htmlEscape(this.domainName, "UTF-8");
        this.email = HtmlUtils.htmlEscape(this.email, "UTF-8");
        this.mobilePhone = HtmlUtils.htmlEscape(this.mobilePhone, "UTF-8");
        this.phone = HtmlUtils.htmlEscape(this.phone, "UTF-8");
        this.provinceId = HtmlUtils.htmlEscape(this.provinceId, "UTF-8");
        this.website = HtmlUtils.htmlEscape(this.website, "UTF-8");
    }

    public String validate() {
        String errors = "";

        if (email != null) {
            if (!MyStringUtil.isEmailFormatCorrect(email)) {
                errors += "'email' has incorrect format! && ";
            }
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

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public String toString() {
        return "SubscriberUpdateModel{" +
                "address='" + address + '\'' +
                ", provinceId='" + provinceId + '\'' +
                ", companyName='" + companyName + '\'' +
                ", district='" + district + '\'' +
                ", domainName='" + domainName + '\'' +
                ", email='" + email + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", phone='" + phone + '\'' +
                ", website='" + website + '\'' +
                '}';
    }
}
