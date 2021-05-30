package com.hohomalls.core.mapper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The base mapper for all mappers.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 27/4/2021
 */
public interface BaseMapper<T, D> {

  @Nullable
  D toDoc(@Nullable T type);

  /** Maps a list of types to a list of docs. */
  @NotNull
  default List<D> toDocs(@Nullable List<T> types) {
    if (types == null || types.isEmpty()) {
      return List.of();
    }

    return types.stream().map(this::toDoc).collect(Collectors.toList());
  }

  @Nullable
  T toType(@Nullable D doc);

  /** Maps a list of docs to a list of types. */
  @NotNull
  default List<T> toTypes(@Nullable List<D> docs) {
    if (docs == null || docs.isEmpty()) {
      return List.of();
    }

    return docs.stream().map(this::toType).collect(Collectors.toList());
  }
}
