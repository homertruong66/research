package com.rms.rms.common.view_model;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.HtmlUtils;

public class SubsGetResponseConfigUpdateModel {

    private String apiKey;
    private String campaignDefaultId;
    private Boolean sendAffiliateData;

    public void escapeHtml() {
        this.apiKey = HtmlUtils.htmlEscape(this.apiKey, "UTF-8");
        this.campaignDefaultId = HtmlUtils.htmlEscape(this.campaignDefaultId, "UTF-8");
    }

    public String validate() {
        String errors = "";

        if (StringUtils.isBlank(apiKey)) {
            errors += "'api_key' can't be null! ";
        }

        if (StringUtils.isBlank(campaignDefaultId)) {
            errors += "'campaign_default_id' can't be null! ";
        }

        return errors.equals("") ? null : errors;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getCampaignDefaultId() {
        return campaignDefaultId;
    }

    public void setCampaignDefaultId(String campaignDefaultId) {
        this.campaignDefaultId = campaignDefaultId;
    }

    public Boolean getSendAffiliateData() {
        return sendAffiliateData;
    }

    public void setSendAffiliateData(Boolean sendAffiliateData) {
        this.sendAffiliateData = sendAffiliateData;
    }

    @Override
    public String toString() {
        return "SubsGetResponseConfigUpdateModel{" +
                "apiKey='" + apiKey + '\'' +
                ", campaignDefaultId='" + campaignDefaultId + '\'' +
                ", sendAffiliateData=" + sendAffiliateData +
                '}';
    }
}
