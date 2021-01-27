package com.rng.apirng.services;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class S3Service {

    private Logger LOGGER = LoggerFactory.getLogger(S3Service.class);

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${s3.bucket}")
    private String bucketName;

    public void uploadFile(String localFilePath) {
        try {
            File file = new File(localFilePath);
            LOGGER.info("Iniciando upload2");
            amazonS3.putObject(new PutObjectRequest(bucketName, "testando.png", file));
            LOGGER.info("Upload finalizando2");

        } catch(AmazonServiceException e) {

            LOGGER.info("AmazonServiceException: " + e.getErrorMessage());
            LOGGER.info("Status code: " + e.getErrorCode());

        } catch(AmazonClientException e) {
            LOGGER.info("AmazonClientException: " + e.getMessage());
        }
    }
}