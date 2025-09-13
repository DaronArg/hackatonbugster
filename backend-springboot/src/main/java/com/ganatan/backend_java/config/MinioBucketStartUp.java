package com.ganatan.backend_java.config;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


/**
 * Configuration class for MinIO bucket setup.
 */
@Configuration
public class MinioBucketStartUp {

    /**
     * Minio client used to interact with the MinIO server.
     */
    private final MinioClient minioClient;

    /**
     * Name of the bucket used for owners data.
     */
    @Value("${minio.config.bucket-name}")
    private String kycFilesBucketName;

    /**
     * Env variable to enable Minio.
     */
    @Value("${minio.config.enabled}")
    private boolean minioEnabled;


    /**
     * Constructor for MinioBucketConfig.
     *
     * @param minioClientParam the Minio client to interact with MinIO.
     */
    public MinioBucketStartUp(MinioClient minioClientParam) {
        this.minioClient = minioClientParam;
    }

    /**
     * Initializes the bucket creation process after bean construction.
     */
    @PostConstruct
    public void init() throws ServerException, InsufficientDataException,
        ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException,
        XmlParserException, InternalException {
        if (minioEnabled) {
            createBucketsIfNotExists();
        }
    }

    private void uploadInitialFile(String resourcePath, String objectName) throws IOException, ServerException,
            InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException,
            InvalidResponseException, XmlParserException, InternalException {
        try (InputStream inputStream = getClass().getResourceAsStream(resourcePath)) {
            if (inputStream != null) {
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(kycFilesBucketName)
                                .object(objectName)
                                .stream(inputStream, inputStream.available(), -1)
                                .contentType(Files.probeContentType(new java.io.File(resourcePath).toPath()))
                                .build()
                );
            } else {
                System.err.println("Resource not found: " + resourcePath);
            }
        }
    }

    /**
     * Ensures the required buckets exist in MinIO, creates them if necessary.
     */
    public void createBucketsIfNotExists() throws ServerException, InsufficientDataException,
        ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException,
        XmlParserException, InternalException {
        boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(kycFilesBucketName).build());
        if (!isExist) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(kycFilesBucketName).build());
        }
    }

}
