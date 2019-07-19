package com.rms.rms.service.model;

import com.rms.rms.persistence.BaseEntityExtensible;

/**
 * homertruong
 */

public class SubsConfig extends BaseEntityExtensible {

    public static final String TYPE_COAN_TOWER = "TOWER";
    public static final String TYPE_COAN_LEVEL = "LEVEL";

    private Double accountKeepingFee;
    private String coanType;
    private Boolean isBitlyEnabled;
    private String logo;
    private Double lowestPayment;
    private Subscriber subscriber;
    private String termsAndConditions;

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

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    public String getTermsAndConditions() {
        return termsAndConditions;
    }

    public void setTermsAndConditions(String termsAndConditions) {
        this.termsAndConditions = termsAndConditions;
    }

    @Override
    public String toString() {
        return "SubsConfig{" +
                "accountKeepingFee=" + accountKeepingFee +
                ", coanType='" + coanType + '\'' +
                ", isBitlyEnabled=" + isBitlyEnabled +
                ", logo='" + logo + '\'' +
                ", lowestPayment=" + lowestPayment +
                ", termsAndConditions='" + termsAndConditions + '\'' +
                '}';
    }
}
