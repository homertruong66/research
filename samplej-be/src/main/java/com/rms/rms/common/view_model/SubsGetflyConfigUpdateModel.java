package com.rms.rms.common.view_model;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.HtmlUtils;

public class SubsGetflyConfigUpdateModel {

    private String apiKey;
    private String baseUrl;

    public void escapeHtml() {
        this.apiKey = HtmlUtils.htmlEscape(this.apiKey, "UTF-8");
        this.baseUrl = HtmlUtils.htmlEscape(this.baseUrl, "UTF-8");
    }

    public String validate() {
        String errors = "";

        if (StringUtils.isBlank(apiKey)) {
            errors += "'api_key' can't be null! ";
        }

        if (StringUtils.isBlank(baseUrl)) {
            errors += "'base_url' can't be null! ";
        }

        return errors.equals("") ? null : errors;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public String toString() {
        return "SubsGetflyConfigUpdateModel{" +
                "apiKey='" + apiKey + '\'' +
                ", baseUrl='" + baseUrl + '\'' +
                '}';
    }
}
