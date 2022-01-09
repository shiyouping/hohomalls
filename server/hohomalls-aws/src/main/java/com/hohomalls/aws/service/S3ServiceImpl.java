package com.hohomalls.aws.service;

import com.hohomalls.core.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

/**
 * S3ServiceImpl.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 9/1/2022
 */
@Slf4j
@Service
public class S3ServiceImpl implements StorageService {

  @Override
  public void save(byte[] data, @NotNull String fullPath, @NotNull String fileName) {}
}
