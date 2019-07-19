package com.rms.rms.common.view_model;

public class PackageConfigUpdateModel {

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
    private Boolean hasInfusion;
    private Boolean hasShareStats;
    private Boolean hasVoucher;
    private Integer layerCount;
    private Double price;
    private String usagePeriod;

    public void escapeHtml() {
    }

    public String validate() {
        String errors = "";

        if (affiliateCount != null && affiliateCount.intValue() < 0) {
            errors += "'affiliate_count' can't be less than 0! && ";
        }

        if (channelCount != null && channelCount.intValue() < 0) {
            errors += "'channel_count' can't be less than 0! && ";
        }

        if (layerCount != null && layerCount.intValue() < 0) {
            errors += "'layer_count' can't be less than 0!";
        }

        return errors.equals("") ? null : errors;
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

    public String getUsagePeriod() {
        return usagePeriod;
    }

    public void setUsagePeriod(String usagePeriod) {
        this.usagePeriod = usagePeriod;
    }

    @Override
    public String toString() {
        return "PackageConfigUpdateModel{" +
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
                ", hasInfusion=" + hasInfusion +
                ", hasShareStats=" + hasShareStats +
                ", hasVoucher=" + hasVoucher +
                ", layerCount=" + layerCount +
                ", price=" + price +
                ", usagePeriod='" + usagePeriod + '\'' +
                '}';
    }
}
