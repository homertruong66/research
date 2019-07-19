package com.rms.rms.common.view_model;

import com.rms.rms.common.util.MyStringUtil;
import com.rms.rms.service.model.SubsConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.HtmlUtils;

/**
 * homertruong
 */

public class SubscriberCreateModel {

    private String address;
    private String coanType = "TOWER";
    private String companyName;
    private String district;
    private String domainName;
    private String email;
    private String firstChannelDomainName;
    private String mobilePhone;
    private String packageType;
    private String provinceId;
    private String phone;
    private String website;

    public void escapeHtml() {
        this.address = HtmlUtils.htmlEscape(this.address, "UTF-8");
        this.companyName = HtmlUtils.htmlEscape(this.companyName, "UTF-8");
        this.district = HtmlUtils.htmlEscape(this.district, "UTF-8");
        this.domainName = HtmlUtils.htmlEscape(this.domainName, "UTF-8");
        this.firstChannelDomainName = HtmlUtils.htmlEscape(this.firstChannelDomainName,"UTF-8");
        this.mobilePhone = HtmlUtils.htmlEscape(this.mobilePhone, "UTF-8");
        this.phone = HtmlUtils.htmlEscape(this.phone, "UTF-8");
        this.provinceId = HtmlUtils.htmlEscape(this.provinceId, "UTF-8");
        this.website = HtmlUtils.htmlEscape(this.website, "UTF-8");
    }

    public String validate() {
        String errors = "";

        if (StringUtils.isBlank(address)) {
            errors += "'address' can't be empty! && ";
        }

        if (StringUtils.isBlank(coanType)) {
            errors += "'coan_type' can't be empty! &&";
        }
        else {
            if (!SubsConfig.TYPE_COAN_TOWER.equals(coanType) && !SubsConfig.TYPE_COAN_LEVEL.equals(coanType)) {
                errors += "'coan_type' must be 'TOWER' or 'LEVEL' ! &&";
            }
        }

        if (StringUtils.isBlank(companyName)) {
            errors += "'company_name' can't be empty! &&";
        }

        if (StringUtils.isBlank(district)) {
            errors += "'district' can't be empty! &&";
        }

        if (StringUtils.isBlank(domainName)) {
            errors += "'domain_name' can't be empty! &&";
        }

        if (StringUtils.isBlank(email)) {
            errors += "'email' can't be empty! &&";
        }

        if (!MyStringUtil.isEmailFormatCorrect(email)) {
            errors += "'email' has incorrect format! &&";
        }

        if (StringUtils.isBlank(mobilePhone)) {
            errors += "'mobile_phone' can't be empty! &&";
        }

        if (StringUtils.isBlank(packageType)) {
            errors += "'package_type' can't be empty! &&";
        }

        if (StringUtils.isBlank(provinceId)) {
            errors += "'provinceId' can't be empty! && ";
        }

        if (StringUtils.isBlank(website)) {
            errors += "'website' can't be empty! &&";
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

    public String getCoanType() {
        return coanType;
    }

    public void setCoanType(String coanType) {
        this.coanType = coanType;
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

    public String getFirstChannelDomainName() {
        return firstChannelDomainName;
    }

    public void setFirstChannelDomainName(String firstChannelDomainName) {
        this.firstChannelDomainName = firstChannelDomainName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
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
        return "SubscriberCreateModel{" +
                "address='" + address + '\'' +
                ", provinceId='" + provinceId + '\'' +
                ", coan='" + coanType + '\'' +
                ", companyName='" + companyName + '\'' +
                ", district='" + district + '\'' +
                ", domainName='" + domainName + '\'' +
                ", email='" + email + '\'' +
                ", firstChannelDomainName='" + firstChannelDomainName + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", packageType='" + packageType + '\'' +
                ", phone='" + phone + '\'' +
                ", website='" + website + '\'' +
                '}';
    }
}
