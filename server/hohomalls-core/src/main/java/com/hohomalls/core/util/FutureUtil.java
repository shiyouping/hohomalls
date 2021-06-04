package com.hohomalls.core.util;

import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The interface of FutureUtil.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 31/5/2021
 */
public interface FutureUtil {

  /** Convert a future to another future. */
  @NotNull
  static <A, B> CompletableFuture<B> from(
      @NotNull CompletableFuture<A> future, @NotNull Function<A, B> mapper) {
    checkNotNull(future, "future cannot be null");
    checkNotNull(mapper, "mapper cannot be null");
    return future.thenApply(mapper);
  }

  /** Convert a mono to a future. */
  @NotNull
  static <A, B> CompletableFuture<B> from(@NotNull Mono<A> mono, @NotNull Function<A, B> mapper) {
    checkNotNull(mono, "mono cannot be null");
    checkNotNull(mapper, "mapper cannot be null");
    return from(mono.toFuture(), mapper);
  }
}
