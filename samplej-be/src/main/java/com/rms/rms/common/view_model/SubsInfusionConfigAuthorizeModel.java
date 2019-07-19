package com.rms.rms.common.view_model;

import org.apache.commons.lang3.StringUtils;

public class SubsInfusionConfigAuthorizeModel {

    private String feUrl;

    public void escapeHtml() {}

    public String validate() {
        String errors = "";

        if (StringUtils.isBlank(feUrl)) {
            errors += "'feUrl' can't be empty!";
        }

        return errors.equals("") ? null : errors;
    }

    public String getFeUrl() {
        return feUrl;
    }

    public void setFeUrl(String feUrl) {
        this.feUrl = feUrl;
    }

    @Override
    public String toString() {
        return "SubsInfusionConfigAuthorizeModel{" +
                "feUrl='" + feUrl + '\'' +
                '}';
    }
}