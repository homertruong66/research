package com.rms.rms.common.dto;

import java.io.Serializable;

/**
 * homertruong
 */

public class AwsS3PreSignedUrlDto implements Serializable {

    private static final long serialVersionUID = 7438766509803908976L;

    private String objectKey;
    private String signedUrl;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getObjectKey() {
        return objectKey;
    }

    public void setObjectKey(String objectKey) {
        this.objectKey = objectKey;
    }

    public String getSignedUrl() {
        return signedUrl;
    }

    public void setSignedUrl(String signedUrl) {
        this.signedUrl = signedUrl;
    }

    @Override
    public String toString() {
        return "AwsS3PreSignedUrlDto{" +
                "objectKey='" + objectKey + '\'' +
                ", signedUrl='" + signedUrl + '\'' +
                '}';
    }
}