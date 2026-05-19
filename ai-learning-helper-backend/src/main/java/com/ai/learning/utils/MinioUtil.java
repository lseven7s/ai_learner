package com.ai.learning.utils;

import io.minio.*;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class MinioUtil {
    
    private final MinioClient minioClient;
    
    private final com.ai.learning.config.MinioConfig minioConfig;
    
    public String uploadFile(MultipartFile file, String fileName) throws Exception {
        InputStream inputStream = file.getInputStream();
        String contentType = file.getContentType();
        
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioConfig.getBucketName()).build());
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioConfig.getBucketName()).build());
        }
        
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(minioConfig.getBucketName())
                        .object(fileName)
                        .stream(inputStream, file.getSize(), -1)
                        .contentType(contentType)
                        .build()
        );
        
        return fileName;
    }
    
    public String getPresignedUrl(String fileName, int expiryDays) throws Exception {
        return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .bucket(minioConfig.getBucketName())
                        .object(fileName)
                        .method(Method.GET)
                        .expiry(expiryDays, TimeUnit.DAYS)
                        .build()
        );
    }
    
    public void deleteFile(String fileName) throws Exception {
        minioClient.removeObject(
                RemoveObjectArgs.builder()
                        .bucket(minioConfig.getBucketName())
                        .object(fileName)
                        .build()
        );
    }
}
