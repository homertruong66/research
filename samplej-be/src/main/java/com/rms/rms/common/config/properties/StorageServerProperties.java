package com.rms.rms.common.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * homertruong
 */

@ConfigurationProperties(prefix = "storage-server")
public class StorageServerProperties {

    private AwsS3Properties awsS3;

    public AwsS3Properties getAwsS3() {
        return awsS3;
    }

    public void setAwsS3(AwsS3Properties awsS3Properties) {
        this.awsS3 = awsS3Properties;
    }

    @ConfigurationProperties(prefix = "storage-server.aws-s3")
    public static class AwsS3Properties {

        private String region;
        private String accessKey;
        private String bucketUploadReport;
        private String secretKey;
        private int expiredTime;

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getAccessKey() {
            return accessKey;
        }

        public void setAccessKey(String accessKey) {
            this.accessKey = accessKey;
        }

        public String getBucketUploadReport() {
            return bucketUploadReport;
        }

        public void setBucketUploadReport(String bucketUploadReport) {
            this.bucketUploadReport = bucketUploadReport;
        }

        public String getSecretKey() {
            return secretKey;
        }

        public void setSecretKey(String secretKey) {
            this.secretKey = secretKey;
        }

        public int getExpiredTime() {
            return expiredTime;
        }

        public void setExpiredTime(int expiredTime) {
            this.expiredTime = expiredTime;
        }
    }
}
