package com.rms.rms.service.model;

import com.rms.rms.persistence.BaseEntityExtensible;

/**
 * homertruong
 */

public class Subscriber extends BaseEntityExtensible {

    public static final String STATUS_ENABLED = "ENABLED";
    public static final String STATUS_DISABLED = "DISABLED";

    private String address;
    private String companyName;
    private String district;
    private String domainName;
    private String email;
    private String mobilePhone;
    private Long numberOfAffiliates;
    private PackageConfigApplied packageConfigApplied;
    private String packageType;
    private String phone;
    private Domain province;
    private String provinceId;
    private String status;
    private SubsConfig subsConfig;
    private SubsEarnerConfig subsEarnerConfig;
    private SubsEmailConfig subsEmailConfig;
    private SubsGetflyConfig subsGetflyConfig;
    private SubsGetResponseConfig subsGetResponseConfig;
    private SubsInfusionConfig subsInfusionConfig;
    private SubsPackageConfig subsPackageConfig;
    private SubsPerformerConfig subsPerformerConfig;
    private SubsRewardConfig subsRewardConfig;
    private String website;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Domain getProvince() {
        return province;
    }

    public void setProvince(Domain province) {
        this.province = province;
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

    public Long getNumberOfAffiliates() {
        return numberOfAffiliates;
    }

    public void setNumberOfAffiliates(Long numberOfAffiliates) {
        this.numberOfAffiliates = numberOfAffiliates;
    }

    public PackageConfigApplied getPackageConfigApplied() {
        return packageConfigApplied;
    }

    public void setPackageConfigApplied(PackageConfigApplied packageConfigApplied) {
        this.packageConfigApplied = packageConfigApplied;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SubsConfig getSubsConfig() {
        return subsConfig;
    }

    public void setSubsConfig(SubsConfig subsConfig) {
        this.subsConfig = subsConfig;
    }

    public SubsEarnerConfig getSubsEarnerConfig () {
        return subsEarnerConfig;
    }

    public void setSubsEarnerConfig (SubsEarnerConfig subsEarnerConfig) {
        this.subsEarnerConfig = subsEarnerConfig;
    }

    public SubsRewardConfig getSubsRewardConfig() {
        return subsRewardConfig;
    }

    public void setSubsRewardConfig(SubsRewardConfig subsRewardConfig) {
        this.subsRewardConfig = subsRewardConfig;
    }

    public SubsEmailConfig getSubsEmailConfig() {
        return subsEmailConfig;
    }

    public void setSubsEmailConfig(SubsEmailConfig subsEmailConfig) {
        this.subsEmailConfig = subsEmailConfig;
    }

    public SubsGetflyConfig getSubsGetflyConfig() {
        return subsGetflyConfig;
    }

    public void setSubsGetflyConfig(SubsGetflyConfig subsGetflyConfig) {
        this.subsGetflyConfig = subsGetflyConfig;
    }

    public SubsGetResponseConfig getSubsGetResponseConfig() {
        return subsGetResponseConfig;
    }

    public void setSubsGetResponseConfig(SubsGetResponseConfig subsGetResponseConfig) {
        this.subsGetResponseConfig = subsGetResponseConfig;
    }

    public SubsInfusionConfig getSubsInfusionConfig() {
        return subsInfusionConfig;
    }

    public void setSubsInfusionConfig(SubsInfusionConfig subsInfusionConfig) {
        this.subsInfusionConfig = subsInfusionConfig;
    }

    public SubsPackageConfig getSubsPackageConfig() {
        return subsPackageConfig;
    }

    public void setSubsPackageConfig(SubsPackageConfig subsPackageConfig) {
        this.subsPackageConfig = subsPackageConfig;
    }

    public SubsPerformerConfig getSubsPerformerConfig() {
        return subsPerformerConfig;
    }

    public void setSubsPerformerConfig(SubsPerformerConfig subsPerformerConfig) {
        this.subsPerformerConfig = subsPerformerConfig;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public String toString() {
        return "Subscriber{" +
                "address='" + address + '\'' +
                ", companyName='" + companyName + '\'' +
                ", district='" + district + '\'' +
                ", domainName='" + domainName + '\'' +
                ", email='" + email + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", numberOfAffiliates='" + numberOfAffiliates + '\'' +
                ", packageType='" + packageType + '\'' +
                ", phone='" + phone + '\'' +
                ", province='" + province + '\'' +
                ", provinceId='" + provinceId + '\'' +
                ", status='" + status + '\'' +
                ", website='" + website + '\'' +
                '}';
    }
}
