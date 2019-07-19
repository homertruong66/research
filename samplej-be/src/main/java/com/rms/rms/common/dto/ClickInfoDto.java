package com.rms.rms.common.dto;

import com.rms.rms.common.BaseDto;

import java.io.Serializable;

/**
 * homertruong
 */

public class ClickInfoDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = 5120336941813834471L;

    private String country;
    private String deviceType;
    private String os;
    private String shareId;
    private Integer count;

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public String getShareId() {
        return shareId;
    }

    public void setShareId(String shareId) {
        this.shareId = shareId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "ClickInfoDto{" +
                "country='" + country + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", os='" + os + '\'' +
                ", shareId='" + shareId + '\'' +
                ", count=" + count +
                '}';
    }
}