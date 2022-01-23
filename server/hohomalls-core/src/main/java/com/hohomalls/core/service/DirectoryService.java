package com.hohomalls.core.service;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * DirectoryService.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 14/1/2022
 */
public interface DirectoryService {

  @NotNull
  List<String> getRootDirectories();

  @NotNull
  List<String> getSubDirectories();
}
