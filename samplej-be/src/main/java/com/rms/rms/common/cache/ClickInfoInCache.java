package com.rms.rms.common.cache;

import java.io.Serializable;

public class ClickInfoInCache implements Serializable {

    private static final long serialVersionUID = -91127555736966836L;

    private Integer count;
    private String country;
    private String deviceType;
    private String id;
    private String os;
    private String shareId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        return "ClickInfoInCache{" +
                "count=" + count +
                ", country='" + country + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", id='" + id + '\'' +
                ", os='" + os + '\'' +
                ", shareId='" + shareId + '\'' +
                '}';
    }
}
