package com.rms.rms.common.view_model;

import com.rms.rms.common.util.MyStringUtil;
import org.springframework.web.util.HtmlUtils;

/**
 * homertruong
 */

public class SubsConfigUpdateModel {

    private Double accountKeepingFee;
    private String coanType;
    private Boolean isBitlyEnabled;
    private String logo;
    private Double lowestPayment;
    private String termsAndConditions;

    // Subscriber
    private String address;
    private String provinceId;
    private String companyName;
    private String district;
    private String domainName;
    private String email;
    private String mobilePhone;
    private String phone;
    private String website;

    public void escapeHtml() {
        this.logo = HtmlUtils.htmlEscape(this.logo, "UTF-8");
        //this.termsAndConditions = HtmlUtils.htmlEscape(this.termsAndConditions, "UTF-8");     // this is HTML

        // Subscriber
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
        // Subscriber
        String errors = "";

        if (email != null) {
            if (!MyStringUtil.isEmailFormatCorrect(email)) {
                errors += "'email' has incorrect format! && ";
            }
        }

        return errors.equals("") ? null : errors;
    }

    //

    public Double getAccountKeepingFee() {
        return accountKeepingFee;
    }

    public void setAccountKeepingFee(Double accountKeepingFee) {
        this.accountKeepingFee = accountKeepingFee;
    }

    public String getCoanType() {
        return coanType;
    }

    public void setCoanType(String coanType) {
        this.coanType = coanType;
    }

    public Boolean getIsBitlyEnabled() {
        return isBitlyEnabled;
    }

    public void setIsBitlyEnabled(Boolean isBitlyEnabled) {
        this.isBitlyEnabled = isBitlyEnabled;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Double getLowestPayment() {
        return lowestPayment;
    }

    public void setLowestPayment(Double lowestPayment) {
        this.lowestPayment = lowestPayment;
    }

    public String getTermsAndConditions() {
        return termsAndConditions;
    }

    public void setTermsAndConditions(String termsAndConditions) {
        this.termsAndConditions = termsAndConditions;
    }

    // Subscriber
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
        return "SubsConfigUpdateModel{" +
                "accountKeepingFee=" + accountKeepingFee +
                ", coanType=" + coanType +
                ", isBitlyEnabled=" + isBitlyEnabled + '\'' +
                ", logo='" + logo + '\'' +
                ", lowestPayment=" + lowestPayment +
                ", termsAndConditions='" + termsAndConditions + '\'' +
                '}' +

                " - SubscriberUpdateModel{" +
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
