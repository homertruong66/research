package com.rms.rms.common.dto;

import com.rms.rms.common.BaseDto;

import java.io.Serializable;

/**
 * homertruong
 */

public class SubscriberDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = -8240005328363154473L;

    private String companyName;
    private PackageConfigAppliedDto packageConfigApplied;
    private String domainName;
    private String mobilePhone;
    private String packageType;
    private String website;
    private String address;
    private String district;
    private String email;
    private String firstName;
    private String lastName;
    private BillDto latestBill;
    private String personalEmail;
    private String phone;
    private DomainDto province;
    private String status;
    private SubsConfigDto subsConfig;
    private UserDto user;
    private ChannelDto firstChannel;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public PackageConfigAppliedDto getPackageConfigApplied() {
        return packageConfigApplied;
    }

    public void setPackageConfigApplied(PackageConfigAppliedDto packageConfigApplied) {
        this.packageConfigApplied = packageConfigApplied;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
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

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public BillDto getLatestBill() {
        return latestBill;
    }

    public void setLatestBill(BillDto latestBill) {
        this.latestBill = latestBill;
    }

    public String getPersonalEmail() {
        return personalEmail;
    }

    public void setPersonalEmail(String personalEmail) {
        this.personalEmail = personalEmail;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public SubsConfigDto getSubsConfig() {
        return subsConfig;
    }

    public void setSubsConfig(SubsConfigDto subsConfig) {
        this.subsConfig = subsConfig;
    }

    public ChannelDto getFirstChannel() {
        return firstChannel;
    }

    public void setFirstChannel(ChannelDto firstChannel) {
        this.firstChannel = firstChannel;
    }

    @Override
    public String toString() {
        return "SubsAdminDto{" +
                "companyName='" + companyName + '\'' +
                "packageConfigApplied='" + packageConfigApplied + '\'' +
                ", domainName='" + domainName + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", packageType='" + packageType + '\'' +
                ", website='" + website + '\'' +
                ", address='" + address + '\'' +
                ", district='" + district + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", latestBill='" + latestBill + '\'' +
                ", personalEmail='" + personalEmail + '\'' +
                ", phone='" + phone + '\'' +
                ", province='" + province + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}