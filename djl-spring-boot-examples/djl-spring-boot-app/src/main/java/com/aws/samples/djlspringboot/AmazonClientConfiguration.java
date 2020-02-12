package com.aws.samples.djlspringboot;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
/**
 * Consider spring cloud for aws as a potential option to configure.
 */
public class AmazonClientConfiguration {

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Bean
    public AmazonS3 s3() {
        return AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1)
                .withCredentials(new EnvironmentVariableCredentialsProvider())
                .build();
    }

    @Bean
    public S3ImageDownloader downloader(AmazonS3 s3) {
        return new S3ImageDownloader(s3, bucketName, "inbox");
    }

    @Bean
    public S3ImageUploader uploader(AmazonS3 s3) {
        return new S3ImageUploader(s3, bucketName, "outbox");
    }
}


