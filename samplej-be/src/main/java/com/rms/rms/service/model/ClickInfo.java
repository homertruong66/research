package com.rms.rms.service.model;

import com.rms.rms.persistence.BaseEntityExtensible;

/**
 * homertruong
 */

public class ClickInfo extends BaseEntityExtensible {

    public static final String OS_WINDOWS = "Windows";

    private String country;
    private String deviceType;
    private String os;
    private String shareId;
    private String statsDate;
    private Integer count;

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

    public String getShareId() {
        return shareId;
    }

    public void setShareId(String shareId) {
        this.shareId = shareId;
    }

    public String getStatsDate () {
        return statsDate;
    }

    public void setStatsDate (String statsDate) {
        this.statsDate = statsDate;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "ClickInfo{" +
                "country='" + country + '\'' +
                ", deviceType=" + deviceType +
                ", os='" + os + '\'' +
                ", shareId='" + shareId + '\'' +
                ", statsDate='" + statsDate + '\'' +
                ", count=" + count +
                '}';
    }
}
