package com.ganatan.backend_java.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up MiniO client.
 */
@Configuration
public class MiniOConfig {

  /**
   * The endpoint URL for the MinIO server.
   */
  @Value("${minio.config.endpoint}")
  private String endpoint;
  /**
   * The access key for the MinIO server.
   */
  @Value("${minio.config.access-key}")
  private String accessKey;
  /**
   * The secret key for the MinIO server.
   */
  @Value("${minio.config.secret-key}")
  private String secretKey;

  /**
   * Creates and configures a MiniOClient bean.
   *
   * @return A MiniOClient instance connected to the specified endpoint.
   */
  @Bean
  public MinioClient miniOClient() {
    return MinioClient.builder()
        .endpoint(endpoint)
        .credentials(accessKey, secretKey)
        .build();
  }
}
