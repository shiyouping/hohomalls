package com.hohomalls.aws.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.hohomalls.aws.config.AwsProperties;
import com.hohomalls.core.service.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * S3ServiceImpl.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 9/1/2022
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements StorageService {

  private final AwsProperties awsProperties;
  private final AmazonS3Client amazonS3Client;

  private String bucketUrl;

  @PostConstruct
  public void postConstruct() {
    var bucketName = this.awsProperties.s3().bucketName();
    this.bucketUrl = "https://%s.s3.amazonaws.com".formatted(bucketName);
  }

  @Override
  public void save(byte[] data, @NotNull String filePath, @NotNull String fileName) {}
}
