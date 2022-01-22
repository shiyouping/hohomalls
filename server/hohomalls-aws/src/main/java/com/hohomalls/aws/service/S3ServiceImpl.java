package com.hohomalls.aws.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.hohomalls.aws.config.AwsProperties;
import com.hohomalls.core.common.Constant;
import com.hohomalls.core.service.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;

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

  @PostConstruct
  public void initS3Bucket() {
    var bucketName = this.awsProperties.s3().bucketName();
    var buckets = this.amazonS3Client.listBuckets();

    if (CollectionUtils.isEmpty(buckets)
        || buckets.stream().noneMatch(bucket -> bucketName.equals(bucket.getName()))) {
      this.amazonS3Client.createBucket(bucketName);
    }
  }

  @Override
  public Mono<Void> save(byte[] data, @NotNull String filePath, @NotNull String fileName) {
    return Mono.fromRunnable(
        () -> {
          var key = String.join(Constant.SIGN_SLASH, filePath, fileName);
          var bucketName = this.awsProperties.s3().bucketName();
          S3ServiceImpl.log.info("Uploading a file to S3. BucketName={}, Key={}", bucketName, key);
          this.amazonS3Client.putObject(this.createPutObjectRequest(data, key, bucketName));
        });
  }

  private PutObjectRequest createPutObjectRequest(
      byte[] data, @NotNull String key, String bucketName) {
    return new PutObjectRequest(
        bucketName, key, new ByteArrayInputStream(data), new ObjectMetadata());
  }
}
