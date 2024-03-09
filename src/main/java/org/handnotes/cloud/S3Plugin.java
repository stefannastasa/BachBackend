package org.handnotes.cloud;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Date;


@Service
public class S3Plugin {

    private final AmazonS3 amazonS3;
    private final AWSClientConfig awsClientConfig;

    @Value("${aws.urlExpiration}")
    private long millisTime;

    public S3Plugin(AmazonS3 amazonS3, AWSClientConfig awsClientConfig) {
        this.amazonS3 = amazonS3;
        this.awsClientConfig = awsClientConfig;
    }

    public String uploadImage(MultipartFile file) throws SdkClientException, AmazonServiceException {
        System.out.println("Trying to upload file to aws...");
        File localFile = convertMultipartFileToFile(file);
        if(localFile != null){
            PutObjectResult result = amazonS3.putObject(new PutObjectRequest(awsClientConfig.getBucketName(), file.getOriginalFilename(), localFile));
            localFile.delete();

        }

        return file.getOriginalFilename();
    }

    public String createSignedUrl(String keyName){
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();

        expTimeMillis += millisTime;
        expiration.setTime(expTimeMillis);

        GeneratePresignedUrlRequest presignedUrlRequest = new GeneratePresignedUrlRequest(awsClientConfig.getBucketName(), keyName)
                .withMethod(HttpMethod.GET)
                .withExpiration(expiration);

        URL url = amazonS3.generatePresignedUrl(presignedUrlRequest);

        return url.toString();
    }

    private File convertMultipartFileToFile(MultipartFile file){
        if(file.getOriginalFilename() == null)
            return null;
        File convertedFile = new File(file.getOriginalFilename());
        try{
            Files.copy(file.getInputStream(), convertedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return convertedFile;
    }
}
