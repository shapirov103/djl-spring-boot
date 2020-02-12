package com.aws.samples.djlspringboot;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class S3ImageDownloader {

    private static final Logger LOG = LoggerFactory.getLogger(S3ImageDownloader.class);

    private AmazonS3 s3;

    private String bucketName;

    private String folder;

    public S3ImageDownloader(AmazonS3 s3, String bucketName, String folder) {
        this.s3 = s3;
        this.bucketName = bucketName;
        this.folder = folder == null ? "" : folder.concat("/");
    }

    public BufferedImage download(String fileName) throws IOException {
        String key = folder.concat(fileName);
        LOG.info("Downloading {} from S3 bucket {}...\n", key, bucketName);
        try (S3ObjectInputStream s3is = s3.getObject(bucketName, key).getObjectContent();) {
            return ImageIO.read(s3is);
        }
    }
}
