package com.teamtrace.realland.service.impl;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.teamtrace.realland.api.request.FileDeleteRequest;
import com.teamtrace.realland.api.response.FileUploadResponse;
import com.teamtrace.realland.api.response.UpdateResponse;
import com.teamtrace.realland.service.FileService;
import com.teamtrace.realland.util.configprops.FileProperties;
import com.teamtrace.realland.util.constant.Statuses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Service
public class FileServiceImpl implements FileService {
    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Resource
    private FileProperties fileProperties;

    public FileUploadResponse uploadImageS3(MultipartFile multipartFile, int merchantId, int primaryId, String category) {
        FileUploadResponse response = new FileUploadResponse();
        response.setStatus(Statuses.RESPONSE_STATUS_FAIL);

        try {
            String fileName = System.nanoTime() + ".jpg";
            String filePath = "";
            if (merchantId != 0) {
                filePath = merchantId + "/";
            }
            filePath = filePath + category + "/";
            if (primaryId != 0) {
                filePath = filePath + primaryId + "/";
            }
            filePath = filePath + fileName;
            ObjectMetadata omd = new ObjectMetadata();
            omd.setContentType(multipartFile.getContentType());
            AWSCredentials credentials = new BasicAWSCredentials(
                    fileProperties.getAwsAccessKey(),
                    fileProperties.getAwsSecretKey());
            AmazonS3 s3client = new AmazonS3Client(credentials);

            s3client.setRegion(Region.AP_Singapore.toAWSRegion());

            s3client.putObject(new PutObjectRequest(fileProperties.getS3Bucket(), filePath,
                    multipartFile.getInputStream(), omd)
                    .withCannedAcl(CannedAccessControlList.PublicRead));

            response.setFileName(filePath);

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            logger.info("File upload successfully type. path : {}", fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    public UpdateResponse deleteImageS3(FileDeleteRequest request) {
        UpdateResponse response = new UpdateResponse();
        response.setStatus(Statuses.RESPONSE_STATUS_FAIL);

        try {
            AWSCredentials credentials = new BasicAWSCredentials(
                    fileProperties.getAwsAccessKey(),
                    fileProperties.getAwsSecretKey());
            AmazonS3 s3client = new AmazonS3Client(credentials);

            s3client.setRegion(Region.AP_Singapore.toAWSRegion());

            ObjectListing objects = s3client.listObjects(fileProperties.getS3Bucket(), request.getFilePath());

            for (S3ObjectSummary objectSummary : objects.getObjectSummaries()) {
                s3client.deleteObject(fileProperties.getS3Bucket(), objectSummary.getKey());
            }

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);

        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage("File can,t delete.");
            e.printStackTrace();
        }

        return response;
    }

}
