package com.hohomalls.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;

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

  @Named("jsonToStringMap")
  @SuppressWarnings("all")
  static Map jsonToStringMap(Object json) {
    return json == null ? null : (Map) json;
  }
}
