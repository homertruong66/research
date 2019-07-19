package com.rms.rms.service.model;

import com.rms.rms.persistence.BaseEntityExtensible;

public class SubsGetResponseConfig extends BaseEntityExtensible {

    private String apiKey;
    private String addressFieldId;
    private String birthdayFieldId;
    private String campaignDefaultId;
    private String facebookLinkFieldId;
    private String passwordFieldId;
    private String phoneFieldId;
    private String referrerEmailFieldId;
    private Boolean sendAffiliateData;
    private Subscriber subscriber;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getAddressFieldId() {
        return addressFieldId;
    }

    public void setAddressFieldId(String addressFieldId) {
        this.addressFieldId = addressFieldId;
    }

    public String getBirthdayFieldId() {
        return birthdayFieldId;
    }

    public void setBirthdayFieldId(String birthdayFieldId) {
        this.birthdayFieldId = birthdayFieldId;
    }

    public String getCampaignDefaultId() {
        return campaignDefaultId;
    }

    public void setCampaignDefaultId(String campaignDefaultId) {
        this.campaignDefaultId = campaignDefaultId;
    }

    public String getFacebookLinkFieldId() {
        return facebookLinkFieldId;
    }

    public void setFacebookLinkFieldId(String facebookLinkFieldId) {
        this.facebookLinkFieldId = facebookLinkFieldId;
    }

    public String getPasswordFieldId() {
        return passwordFieldId;
    }

    public void setPasswordFieldId(String passwordFieldId) {
        this.passwordFieldId = passwordFieldId;
    }

    public String getPhoneFieldId() {
        return phoneFieldId;
    }

    public void setPhoneFieldId(String phoneFieldId) {
        this.phoneFieldId = phoneFieldId;
    }

    public String getReferrerEmailFieldId() {
        return referrerEmailFieldId;
    }

    public void setReferrerEmailFieldId(String referrerEmailFieldId) {
        this.referrerEmailFieldId = referrerEmailFieldId;
    }

    public Boolean getSendAffiliateData() {
        return sendAffiliateData;
    }

    public void setSendAffiliateData(Boolean sendAffiliateData) {
        this.sendAffiliateData = sendAffiliateData;
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public String toString() {
        return "SubsGetResponseConfig{" +
                "apiKey='" + apiKey + '\'' +
                ", addressFieldId='" + addressFieldId + '\'' +
                ", birthdayFieldId='" + birthdayFieldId + '\'' +
                ", campaignDefaultId='" + campaignDefaultId + '\'' +
                ", facebookLinkFieldId='" + facebookLinkFieldId + '\'' +
                ", passwordFieldId='" + passwordFieldId + '\'' +
                ", phoneFieldId='" + phoneFieldId + '\'' +
                ", referrerEmailFieldId='" + referrerEmailFieldId + '\'' +
                ", sendAffiliateData=" + sendAffiliateData +
                ", subscriber=" + subscriber +
                '}';
    }
}