package com.hohomalls.web.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

import static com.hohomalls.core.common.Constant.AUTH_PREFIX;

/**
 * The class of HttpHeaderUtil.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 20/7/2021
 */
public interface HttpHeaderUtil {

  /**
   * Get the auth token without "Bearer" and leading white spaces. e.g.
   * eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJ3d3cuaG9ob21hbGxzLmNvbSIsImp0aSI6IjllYWIzZDliLTMxM2MtNDk1Ny04N2RiLTJhYzRiZWJhMGEzMCIsImV4cCI6MTYzNTM1NDEzNCwic3ViIjoiYWNjZXNzLXRva2VuIiwiZW1haWwiOiJyaWNreUBnbWFpbC5jb20iLCJyb2xlcyI6IlJPTEVfQlVZRVIiLCJuaWNrbmFtZSI6IlJpY2t5In0.RlBYEHPXp6WEJnecnhwggLNCe5wWdLzLhkCGI_AUgYQ4xAwuNEASRv3puTwHk1__g6yib_L2oGNZOF80p38XQBwSTynTfKmlw6p9EgjY0RluOfC9uJEgQ0kwMvpGhnrbKue1xjVjsE9-grGCwaW0pWjdEQWN4UUr6IA2OMJbLZgo7RuuFUN4HXd-0bPhMD48vSYFS2it_kh6s2bnm3J5evHl9Oe6bkkIp5MDAFEYWm6zhsuNMWW_hMcbiG07z6apWGG-VvIwKbf8Q4X1nnWCUj_mizImCJ9rrJE_ExYrdE0OCsZR-gzabpXN_07XYVgN4uaLPlH9Twvc4_aEf3fmbA
   */
  @NotNull
  static Optional<String> getAuth(@Nullable String header) {
    if (header == null || header.length() <= AUTH_PREFIX.length() + 1) {
      return Optional.empty();
    }

    return Optional.of(header.substring(AUTH_PREFIX.length()).trim());
  }
}
