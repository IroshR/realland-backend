package com.teamtrace.realland.util.configprops;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

@Configuration
@PropertySource("classpath:configprops-${spring.profiles.active}.properties")
@ConfigurationProperties(prefix = "file")
@Validated
public class FileProperties {
    private String awsAccessKey;
    private String awsSecretKey;
    private String s3Bucket;
    private String s3BucketUrl;
    private String fileRoot;

    public String getAwsAccessKey() {
        return awsAccessKey;
    }

    public void setAwsAccessKey(String awsAccessKey) {
        this.awsAccessKey = awsAccessKey;
    }

    public String getAwsSecretKey() {
        return awsSecretKey;
    }

    public void setAwsSecretKey(String awsSecretKey) {
        this.awsSecretKey = awsSecretKey;
    }

    public String getS3Bucket() {
        return s3Bucket;
    }

    public void setS3Bucket(String s3Bucket) {
        this.s3Bucket = s3Bucket;
    }

    public String getS3BucketUrl() {
        return s3BucketUrl;
    }

    public void setS3BucketUrl(String s3BucketUrl) {
        this.s3BucketUrl = s3BucketUrl;
    }

    public String getFileRoot() {
        return fileRoot;
    }

    public void setFileRoot(String fileRoot) {
        this.fileRoot = fileRoot;
    }
}
