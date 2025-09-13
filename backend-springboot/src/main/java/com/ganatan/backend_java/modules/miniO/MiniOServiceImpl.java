
package com.ganatan.backend_java.modules.miniO;

import io.minio.*;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Implementation of the MiniOService interface for interacting with MiniO.
 */
@Service
public class MiniOServiceImpl {

  /**
   * The MinioClient used for interacting with MinIO.
   */
  private final MinioClient minioClient;

  /**
   * The name of the bucket in MiniO where files are stored.
   */
  @Value("${minio.config.bucket-name}")
  private String bucketName;

  /**
   * Constructs an instance of MiniOServiceImpl
   * with the specified dependencies.
   *
   * @param minioClientParam      the MinioClient used for interacting with MinIO
   */
  @Autowired
  public MiniOServiceImpl(MinioClient minioClientParam) {
    this.minioClient = minioClientParam;
  }

  /**
   * Retrieves a file from MiniO storage.
   *
   * @param fileName the name of the file to retrieve
   * @return the response containing the file object
   * @throws Exception if a MiniO error occurs
   */
  public GetObjectResponse getFile(String fileName) throws IOException,
      ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException,
      InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

    GetObjectArgs getObjectArgs = GetObjectArgs.builder()
        .bucket(bucketName)
        .object(fileName)
        .build();

    return minioClient.getObject(getObjectArgs);
  }

  /**
   * Saves a file to MinIO storage.
   * @param fileName   the name of the file to save
   * @param filePrefix the prefix to add to the file name
   * @param file       the file to save
   * @return the response containing the result of the save operation
   * @throws Exception if a MiniO error occurs
   */
  public ObjectWriteResponse saveFile(String fileName, String filePrefix, MultipartFile file) throws IOException,
      ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException,
      InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

    PutObjectArgs putObjectArgs = PutObjectArgs.builder()
        .bucket(bucketName)
        .object(filePrefix + "/" + fileName)
        .contentType(file.getContentType())
        .stream(file.getInputStream(), file.getSize(), -1)
        .build();

    return minioClient.putObject(putObjectArgs);
  }

  /**
   * Deletes a file from MinIO storage.
   *
   * @param fileName the name of the file to delete
   * @throws Exception if a MiniO error occurs
   */
  public void deleteFile(String fileName) throws IOException,
      ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException,
      InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

    RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder()
        .bucket(bucketName)
        .object(fileName)
        .build();


    minioClient.removeObject(removeObjectArgs);
  }

}
