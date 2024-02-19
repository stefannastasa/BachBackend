package org.handnotes.cloud;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;


@Service
public class S3Plugin {

    private final AmazonS3 amazonS3;
    private final AWSClientConfig awsClientConfig;
    public S3Plugin(AmazonS3 amazonS3, AWSClientConfig awsClientConfig) {
        this.amazonS3 = amazonS3;
        this.awsClientConfig = awsClientConfig;
    }

    public String uploadImage(MultipartFile file) throws SdkClientException, AmazonServiceException {
        File localFile = convertMultipartFileToFile(file);

        PutObjectResult result = amazonS3.putObject(new PutObjectRequest(awsClientConfig.getBucketName(), file.getOriginalFilename(), localFile));

        return file.getOriginalFilename();
    }

    public String createSignedUrl(String imageId){


        return "";
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
