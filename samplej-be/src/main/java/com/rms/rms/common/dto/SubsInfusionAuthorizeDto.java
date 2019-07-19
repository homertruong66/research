package com.rms.rms.common.dto;

import java.io.Serializable;

public class SubsInfusionAuthorizeDto implements Serializable {

    private static final long serialVersionUID = 3405138147953911694L;

    private String authorizeUrl;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getAuthorizeUrl() {
        return authorizeUrl;
    }

    public void setAuthorizeUrl(String authorizeUrl) {
        this.authorizeUrl = authorizeUrl;
    }

    @Override
    public String toString() {
        return "SubsInfusionAuthorizeDto{" +
                "authorizeUrl='" + authorizeUrl + '\'' +
                '}';
    }
}