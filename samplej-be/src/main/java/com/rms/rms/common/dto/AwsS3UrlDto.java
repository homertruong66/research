package com.rms.rms.common.dto;

import java.io.Serializable;

/**
 * homertruong
 */

public class AwsS3UrlDto implements Serializable {

    private static final long serialVersionUID = -8173764373632855041L;

    private String url;

    public AwsS3UrlDto() {
    }

    public AwsS3UrlDto(String url) {
        this.url = url;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "AwsS3UrlDto{" +
                "url='" + url + '\'' +
                '}';
    }
}