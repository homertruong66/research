package com.rms.rms.common.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "getresponse-server")
public class GetResponseServerProperties {

    private String campaignUrl;
    private String contactUrl;
    private String customFieldUrl;

    public String getCampaignUrl() {
        return campaignUrl;
    }

    public void setCampaignUrl(String campaignUrl) {
        this.campaignUrl = campaignUrl;
    }

    public String getContactUrl() {
        return contactUrl;
    }

    public void setContactUrl(String contactUrl) {
        this.contactUrl = contactUrl;
    }

    public String getCustomFieldUrl() {
        return customFieldUrl;
    }

    public void setCustomFieldUrl(String customFieldUrl) {
        this.customFieldUrl = customFieldUrl;
    }
}