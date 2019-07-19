package com.rms.rms.common.view_model;

import org.apache.commons.lang3.StringUtils;

/**
 * homertruong
 */

public class AwsS3PreSignedUrlModel {

    private String bucketName;
    private String objectKey;

    public String validate() {
        String errors = "";

        if (StringUtils.isBlank(bucketName)) {
            errors += "'bucket_name' can't be empty! && ";
        }

        return errors.equals("") ? null : errors;
    }

    //
    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getObjectKey() {
        return objectKey;
    }

    public void setObjectKey(String objectKey) {
        this.objectKey = objectKey;
    }
}
