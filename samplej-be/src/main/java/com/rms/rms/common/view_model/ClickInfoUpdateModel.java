package com.rms.rms.common.view_model;

import org.springframework.web.util.HtmlUtils;

/**
 * homertruong
 */

public class ClickInfoUpdateModel {

    private String country;
    private String deviceType;
    private String os;

    public void escapeHtml() {
        this.country = HtmlUtils.htmlEscape(this.country, "UTF-8");
        this.deviceType = HtmlUtils.htmlEscape(this.deviceType, "UTF-8");
        this.os = HtmlUtils.htmlEscape(this.os, "UTF-8");
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    @Override
    public String toString() {
        return "ClickInfoUpdateModel{" +
                "country='" + country + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", os='" + os + '\'' +
                '}';
    }
}
