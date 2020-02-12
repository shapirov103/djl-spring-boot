package com.aws.samples.djlspringboot;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class S3ImageUploader {
    private static final Logger LOG = LoggerFactory.getLogger(S3ImageDownloader.class);

    private AmazonS3 s3;

    private String bucketName;

    private String folder;

    private static final String S3REF = "https://%s.s3.amazonaws.com/%s";


    public S3ImageUploader(AmazonS3 s3, String bucketName, String folder) {
        this.s3 = s3;
        this.bucketName = bucketName;
        this.folder = folder ==  null ? "" : folder.concat("/");
    }

    public String upload(RenderedImage image, String file) throws IOException {
        String key = folder.concat(file);
        LOG.info("Uploading {} to S3 bucket {}...\n", key, bucketName);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image, "png", os);
        InputStream is = new ByteArrayInputStream(os.toByteArray());
        ObjectMetadata metadata  = new ObjectMetadata();
        s3.putObject(bucketName, key, is, metadata);
        return String.format(S3REF, bucketName, key);
    }
}
