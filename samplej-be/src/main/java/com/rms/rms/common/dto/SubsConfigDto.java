package com.rms.rms.common.dto;

import com.rms.rms.common.BaseDto;

import java.io.Serializable;

/**
 * homertruong
 */

public class SubsConfigDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = -2688079498156356546L;

    private Double accountKeepingFee;
    private String coanType;
    private Boolean isBitlyEnabled;
    private String logo;
    private Double lowestPayment;
    private String termsAndConditions;

    // Subscriber
    private String address;
    private String companyName;
    private String district;
    private String domainName;
    private String email;
    private String mobilePhone;
    private String phone;
    private DomainDto province;
    private String website;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

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

    public DomainDto getProvince() {
        return province;
    }

    public void setProvince(DomainDto province) {
        this.province = province;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public String toString() {
        return "SubsConfigDto{" +
                "accountKeepingFee=" + accountKeepingFee +
                ", coanType=" + coanType +
                ", isBitlyEnabled='" + isBitlyEnabled + '\'' +
                ", logo='" + logo + '\'' +
                ", lowestPayment=" + lowestPayment +
                ", termsAndConditions='" + termsAndConditions + '\'' +
                '}' +
                "SubsAdminDto{" +
                "companyName='" + companyName + '\'' +
                ", domainName='" + domainName + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", website='" + website + '\'' +
                ", address='" + address + '\'' +
                ", province='" + province + '\'' +
                ", district='" + district + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}