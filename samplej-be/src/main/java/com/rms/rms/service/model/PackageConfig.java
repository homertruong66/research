package com.rms.rms.service.model;

import com.rms.rms.persistence.BaseEntityExtensible;

/**
 * homertruong
 */

public class PackageConfig extends BaseEntityExtensible {

    public static final String TYPE_TRIAL = "TRIAL";
    public static final String TYPE_BASIC = "BASIC";
    public static final String TYPE_VIP = "VIP";
    public static final String TYPE_UP = "UP";

    public static final String USAGE_PERIOD_MONTH = "MONTH";
    public static final String USAGE_PERIOD_YEAR = "YEAR";

    public static final int MIN_AFFILIATE_COUNT = 1;
    public static final int TRIAL_MAX_AFFILIATE_COUNT = 20;
    public static final int BASIC_MAX_AFFILIATE_COUNT = 150;
    public static final int VIP_MAX_AFFILIATE_COUNT = 350;
    public static final int UP_MAX_AFFILIATE_COUNT = 550;

    public static final int MIN_CHANNEL_COUNT = 1;
    public static final int TRIAL_MAX_CHANNEL_COUNT = 1;
    public static final int BASIC_MAX_CHANNEL_COUNT = 2;
    public static final int VIP_MAX_CHANNEL_COUNT = 3;
    public static final int UP_MAX_CHANNEL_COUNT = 5;

    public static final int MIN_LAYER_COUNT = 1;
    public static final int TRIAL_MAX_LAYER_COUNT = 5;
    public static final int BASIC_MAX_LAYER_COUNT = 5;
    public static final int VIP_MAX_LAYER_COUNT = 7;
    public static final int UP_MAX_LAYER_COUNT = 10;

    private Integer affiliateCount;
    private Integer channelCount;
    private Boolean hasMobileApp;
    private Boolean hasCommissionCad;
    private Boolean hasCommissionCfe;
    private Boolean hasCommissionCoan;
    private Boolean hasCommissionCoasv;
    private Boolean hasCommissionCoov;
    private Boolean hasCommissionCopg;
    private Boolean hasCommissionCopp;
    private Boolean hasCommissionCoppr;
    private Boolean hasCommissionCopq;
    private Boolean hasCommissionCops;
    private Boolean hasDiscountCode;
    private Boolean hasGetfly;
    private Boolean hasGetResponse;
    private Boolean hasInfusion;
    private Boolean hasShareStats;
    private Boolean hasVoucher;
    private Integer layerCount;
    private Double price;
    private String type;
    private String usagePeriod;

    public static boolean checkType (String packageType) {
        return  TYPE_TRIAL.equals(packageType) ||
                TYPE_BASIC.equals(packageType) ||
                TYPE_VIP.equals(packageType) ||
                TYPE_UP.equals(packageType);
    }

    public static boolean checkUpgradeType(String currentPackageType, String upgradePackageType) {
        switch (currentPackageType) {
            case PackageConfig.TYPE_TRIAL:
                return  TYPE_BASIC.equals(upgradePackageType) ||
                        TYPE_VIP.equals(upgradePackageType) ||
                        TYPE_UP.equals(upgradePackageType);

            case PackageConfig.TYPE_BASIC:
                return  TYPE_VIP.equals(upgradePackageType) ||
                        TYPE_UP.equals(upgradePackageType);

            case PackageConfig.TYPE_VIP:
                return  TYPE_UP.equals(upgradePackageType);

            case PackageConfig.TYPE_UP:
                return false;

            default:
                throw new RuntimeException("Invalid current package type: " + currentPackageType);
        }

    }

    public static boolean checkUsagePeriod (String usagePeriod) {
        return  USAGE_PERIOD_MONTH.equals(usagePeriod) ||
                USAGE_PERIOD_YEAR.equals(usagePeriod) ;
    }

    public Integer getAffiliateCount() {
        return affiliateCount;
    }

    public void setAffiliateCount(Integer affiliateCount) {
        this.affiliateCount = affiliateCount;
    }

    public Integer getChannelCount() {
        return channelCount;
    }

    public void setChannelCount(Integer channelCount) {
        this.channelCount = channelCount;
    }

    public Boolean getHasMobileApp() {
        return hasMobileApp;
    }

    public void setHasMobileApp(Boolean hasMobileApp) {
        this.hasMobileApp = hasMobileApp;
    }

    public Boolean getHasCommissionCad() {
        return hasCommissionCad;
    }

    public void setHasCommissionCad(Boolean hasCommissionCad) {
        this.hasCommissionCad = hasCommissionCad;
    }

    public Boolean getHasCommissionCfe() {
        return hasCommissionCfe;
    }

    public void setHasCommissionCfe(Boolean hasCommissionCfe) {
        this.hasCommissionCfe = hasCommissionCfe;
    }

    public Boolean getHasCommissionCoan() {
        return hasCommissionCoan;
    }

    public void setHasCommissionCoan(Boolean hasCommissionCoan) {
        this.hasCommissionCoan = hasCommissionCoan;
    }

    public Boolean getHasCommissionCoasv() {
        return hasCommissionCoasv;
    }

    public void setHasCommissionCoasv(Boolean hasCommissionCoasv) {
        this.hasCommissionCoasv = hasCommissionCoasv;
    }

    public Boolean getHasCommissionCoov() {
        return hasCommissionCoov;
    }

    public void setHasCommissionCoov(Boolean hasCommissionCoov) {
        this.hasCommissionCoov = hasCommissionCoov;
    }

    public Boolean getHasCommissionCopg() {
        return hasCommissionCopg;
    }

    public void setHasCommissionCopg(Boolean hasCommissionCopg) {
        this.hasCommissionCopg = hasCommissionCopg;
    }

    public Boolean getHasCommissionCopp() {
        return hasCommissionCopp;
    }

    public void setHasCommissionCopp(Boolean hasCommissionCopp) {
        this.hasCommissionCopp = hasCommissionCopp;
    }

    public Boolean getHasCommissionCoppr() {
        return hasCommissionCoppr;
    }

    public void setHasCommissionCoppr(Boolean hasCommissionCoppr) {
        this.hasCommissionCoppr = hasCommissionCoppr;
    }

    public Boolean getHasCommissionCopq() {
        return hasCommissionCopq;
    }

    public void setHasCommissionCopq(Boolean hasCommissionCopq) {
        this.hasCommissionCopq = hasCommissionCopq;
    }

    public Boolean getHasCommissionCops() {
        return hasCommissionCops;
    }

    public void setHasCommissionCops(Boolean hasCommissionCops) {
        this.hasCommissionCops = hasCommissionCops;
    }

    public Boolean getHasDiscountCode() {
        return hasDiscountCode;
    }

    public void setHasDiscountCode(Boolean hasDiscountCode) {
        this.hasDiscountCode = hasDiscountCode;
    }

    public Boolean getHasGetfly() {
        return hasGetfly;
    }

    public void setHasGetfly(Boolean hasGetfly) {
        this.hasGetfly = hasGetfly;
    }

    public Boolean getHasGetResponse() {
        return hasGetResponse;
    }

    public void setHasGetResponse(Boolean hasGetResponse) {
        this.hasGetResponse = hasGetResponse;
    }

    public Boolean getHasInfusion() {
        return hasInfusion;
    }

    public void setHasInfusion(Boolean hasInfusion) {
        this.hasInfusion = hasInfusion;
    }

    public Boolean getHasShareStats() {
        return hasShareStats;
    }

    public void setHasShareStats(Boolean hasShareStats) {
        this.hasShareStats = hasShareStats;
    }

    public Boolean getHasVoucher() {
        return hasVoucher;
    }

    public void setHasVoucher(Boolean hasVoucher) {
        this.hasVoucher = hasVoucher;
    }

    public Integer getLayerCount() {
        return layerCount;
    }

    public void setLayerCount(Integer layerCount) {
        this.layerCount = layerCount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsagePeriod() {
        return usagePeriod;
    }

    public void setUsagePeriod(String usagePeriod) {
        this.usagePeriod = usagePeriod;
    }

    @Override
    public String toString() {
        return "PackageConfig{" +
                "affiliateCount=" + affiliateCount +
                ", channelCount=" + channelCount +
                ", hasMobileApp=" + hasMobileApp +
                ", hasCommissionCad=" + hasCommissionCad +
                ", hasCommissionCfe=" + hasCommissionCfe +
                ", hasCommissionCoan=" + hasCommissionCoan +
                ", hasCommissionCoasv=" + hasCommissionCoasv +
                ", hasCommissionCoov=" + hasCommissionCoov +
                ", hasCommissionCopg=" + hasCommissionCopg +
                ", hasCommissionCopp=" + hasCommissionCopp +
                ", hasCommissionCoppr=" + hasCommissionCoppr +
                ", hasCommissionCopq=" + hasCommissionCopq +
                ", hasCommissionCops=" + hasCommissionCops +
                ", hasDiscountCode=" + hasDiscountCode +
                ", hasGetfly=" + hasGetfly +
                ", hasGetResponse=" + hasGetResponse +
                ", hasInfusion=" + hasInfusion +
                ", hasShareStats=" + hasShareStats +
                ", hasVoucher=" + hasVoucher +
                ", layerCount=" + layerCount +
                ", price=" + price +
                ", type='" + type + '\'' +
                ", usagePeriod='" + usagePeriod + '\'' +
                '}';
    }

}
