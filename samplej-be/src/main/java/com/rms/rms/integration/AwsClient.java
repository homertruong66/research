package com.rms.rms.integration;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.rms.rms.common.config.properties.StorageServerProperties;
import com.rms.rms.common.dto.AwsS3PreSignedUrlDto;
import com.rms.rms.common.dto.AwsS3UrlDto;
import com.rms.rms.integration.exception.AwsIntegrationException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

@Component
@Scope("prototype")
public class AwsClient {

    private Logger logger = Logger.getLogger(AwsClient.class);

    @Autowired
    private StorageServerProperties storageServerProperties;

    public AwsS3PreSignedUrlDto generateAwsS3PreSignedUrl(String bucketName, String objectKey) throws AwsIntegrationException {
        logger.info("generateAwsS3PreSignedUrl: " + bucketName + " - " + objectKey);

        AwsS3PreSignedUrlDto preSignedUrl = new AwsS3PreSignedUrlDto();
        try {
            String accessKey = storageServerProperties.getAwsS3().getAccessKey();
            String secretKey = storageServerProperties.getAwsS3().getSecretKey();
            String region = storageServerProperties.getAwsS3().getRegion();
            int expiredTime = storageServerProperties.getAwsS3().getExpiredTime();

            BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
            AmazonS3 s3client = AmazonS3ClientBuilder.standard()
                    .withRegion(region)
                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .build();

            if (StringUtils.isBlank(objectKey)) {          // Object key to storage/download a uploaded object form S3
                objectKey = UUID.randomUUID().toString();
            }

            Date expiration = new Date();
            long milliSeconds = expiration.getTime();
            milliSeconds += Long.valueOf(expiredTime);
            expiration.setTime(milliSeconds);

            GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, objectKey);
            generatePresignedUrlRequest.setMethod(HttpMethod.PUT);
            generatePresignedUrlRequest.setExpiration(expiration);

            URL url = s3client.generatePresignedUrl(generatePresignedUrlRequest);
            logger.debug("PreSigned URL = " + url.toString());

            preSignedUrl.setSignedUrl(url.toString());
            preSignedUrl.setObjectKey(objectKey);
        }
        catch (Exception ex) {
            throw new AwsIntegrationException(ex);
        }

        return preSignedUrl;
    }

    public AwsS3UrlDto upload(byte[] data, String fileName, String username) throws AwsIntegrationException {
        logger.info("upload: " + fileName + " - " + username);

        URL url;
        try {
            String accessKey = storageServerProperties.getAwsS3().getAccessKey();
            String secretKey = storageServerProperties.getAwsS3().getSecretKey();
            String region = storageServerProperties.getAwsS3().getRegion();
            String bucket = storageServerProperties.getAwsS3().getBucketUploadReport();
            int expiredTime = storageServerProperties.getAwsS3().getExpiredTime();

            BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
            AmazonS3 s3client = AmazonS3ClientBuilder.standard()
                    .withRegion(region)
                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .build();

            Date expiration = new Date();
            long milliSeconds = expiration.getTime();
            milliSeconds += Long.valueOf(expiredTime);
            expiration.setTime(milliSeconds);

            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentLength(data.length);
            meta.setContentType("application/vnd.ms-excel");

            InputStream is = new ByteArrayInputStream(data);

            s3client.putObject(new PutObjectRequest(
                    String.join("/", bucket, username),
                    fileName,
                    is,
                    meta
            ));

            GeneratePresignedUrlRequest generatePresignedUrlRequest =
                    new GeneratePresignedUrlRequest(String.join("/", bucket, username), fileName)
                            .withMethod(HttpMethod.GET)
                            .withExpiration(expiration);

            url = s3client.generatePresignedUrl(generatePresignedUrlRequest);
        }
        catch (Exception ex) {
            throw new AwsIntegrationException(ex);
        }

        return new AwsS3UrlDto(url.toString());
    }

}
