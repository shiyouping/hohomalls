package com.hohomalls.app.service;

import com.hohomalls.core.service.StorageService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

/**
 * DummyStorageServiceImpl.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 9/1/2022
 */
@Service
public class DummyStorageServiceImpl implements StorageService {
  @Override
  public void save(byte[] data, @NotNull String filePath, @NotNull String fileName) {}
}
