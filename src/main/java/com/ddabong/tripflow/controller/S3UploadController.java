package com.ddabong.tripflow.controller;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/upload")
@RequiredArgsConstructor
public class S3UploadController {
    private final AmazonS3Client amazonS3Client;

    private String testFileUploadPath = "test/";

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @PostMapping("/test")
    public ResponseEntity<String> uploadTestFile(@RequestParam("file") MultipartFile file) {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append(UUID.randomUUID());
            sb.append("-");
            sb.append(file.getOriginalFilename());
            String originalFilename = testFileUploadPath + sb.toString();

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());

            String fileUrl= "https://" + bucket + testFileUploadPath + originalFilename;
            amazonS3Client.putObject(bucket, originalFilename, file.getInputStream(), metadata);

            return ResponseEntity.ok(fileUrl);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
