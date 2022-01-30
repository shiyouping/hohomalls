package com.hohomalls.web.mapper;

import org.jetbrains.annotations.Nullable;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Map;

/**
 * BaseMapper.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 28/1/2022
 */
@Mapper
@Named("BaseMapper")
public interface BaseMapper {

  @Nullable
  @Named("toInstant")
  static Instant toInstant(@Nullable OffsetDateTime offsetDateTime) {
    return offsetDateTime == null ? null : offsetDateTime.toInstant();
  }

  @Nullable
  @Named("toOffsetDateTime")
  static OffsetDateTime toOffsetDateTime(@Nullable Instant instant) {
    return instant == null ? null : OffsetDateTime.ofInstant(instant, ZoneId.of("UTC"));
  }

  @Nullable
  @SuppressWarnings("all")
  @Named("toStringMap")
  static Map toStringMap(@Nullable Object json) {
    return json == null ? null : (Map) json;
  }
}
