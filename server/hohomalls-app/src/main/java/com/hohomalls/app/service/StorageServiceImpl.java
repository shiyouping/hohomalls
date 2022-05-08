package com.hohomalls.app.service;

import com.hohomalls.core.service.StorageService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * StorageServiceImpl.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 9/5/2022
 */
@Service
// FIXME Remove this class
public class StorageServiceImpl implements StorageService {

  @Override
  public Mono<Void> save(byte[] data, @NotNull String filePath, @NotNull String fileName) {
    return null;
  }
}
