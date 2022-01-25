package com.hohomalls.aws.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PublicAccessBlockConfiguration;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.SetPublicAccessBlockRequest;
import com.hohomalls.aws.config.AwsProperties;
import com.hohomalls.core.common.Constant;
import com.hohomalls.core.service.StorageService;
import com.hohomalls.core.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
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

  private static final String BUCKET_POLICY_TEMPLATE = "s3-bucket-policy-template.json";
  private final AwsProperties awsProperties;
  private final AmazonS3Client amazonS3Client;

  @PostConstruct
  public void initS3Bucket() {
    var bucketName = this.awsProperties.s3().bucketName();
    try {
      if (this.amazonS3Client.doesBucketExistV2(bucketName)) {
        return;
      }

      this.createBucket(bucketName);
    } catch (Exception e) { // NOPMD
      S3ServiceImpl.log.error("Failed to create the S3 bucket %s".formatted(bucketName), e);
      throw e; // Fail fast
    }
  }

  @Override
  public Mono<Void> save(byte[] data, @NotNull String filePath, @NotNull String fileName) {
    return Mono.fromRunnable(
        () -> {
          var bucketName = this.awsProperties.s3().bucketName();
          S3ServiceImpl.log.info(
              "Uploading a file to S3. BucketName={}, filePath={}, fileName={}",
              bucketName,
              filePath,
              fileName);

          var key = String.join(Constant.SIGN_SLASH, filePath, fileName);
          if (this.amazonS3Client.doesObjectExist(bucketName, key)) {
            return;
          }

          this.uploadFile(data, key, bucketName);
        });
  }

  private void createBucket(String bucketName) {
    S3ServiceImpl.log.info("Creating a S3 bucket with name={}", bucketName);

    // Create a bucket with public access
    this.amazonS3Client.createBucket(bucketName);

    // Set the policy to allow public read
    var policyText =
        FileUtil.readAsString(S3ServiceImpl.BUCKET_POLICY_TEMPLATE).formatted(bucketName);
    this.amazonS3Client.setBucketPolicy(bucketName, policyText);

    // Disable public access
    var publicAccessConfig =
        new PublicAccessBlockConfiguration()
            .withBlockPublicAcls(true)
            .withIgnorePublicAcls(true)
            .withBlockPublicPolicy(true)
            // Don't enable the following option
            .withRestrictPublicBuckets(false);
    this.amazonS3Client.setPublicAccessBlock(
        new SetPublicAccessBlockRequest()
            .withBucketName(bucketName)
            .withPublicAccessBlockConfiguration(publicAccessConfig));
  }

  private void uploadFile(byte[] data, String key, String bucketName) {
    // For big files, should use multipart upload; otherwise, there will be a memory leakage
    // See an example at:
    // https://medium.com/digio-australia/multipart-upload-to-s3-using-aws-sdk-for-java-d3fd2e17f515
    var request =
        new PutObjectRequest(bucketName, key, new ByteArrayInputStream(data), new ObjectMetadata());
    this.amazonS3Client.putObject(request);
  }
}
