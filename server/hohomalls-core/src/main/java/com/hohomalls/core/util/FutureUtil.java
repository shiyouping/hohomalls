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
  static <D, M> CompletableFuture<M> from(
      @NotNull CompletableFuture<D> future, @NotNull Function<D, M> mapper) {
    checkNotNull(future, "future cannot be null");
    checkNotNull(mapper, "mapper cannot be null");
    return future.thenApply(mapper);
  }

  /** Convert a mono to a future. */
  @NotNull
  static <D, M> CompletableFuture<M> from(@NotNull Mono<D> mono, @NotNull Function<D, M> mapper) {
    checkNotNull(mono, "mono cannot be null");
    checkNotNull(mapper, "mapper cannot be null");
    return from(mono.toFuture(), mapper);
  }
}
