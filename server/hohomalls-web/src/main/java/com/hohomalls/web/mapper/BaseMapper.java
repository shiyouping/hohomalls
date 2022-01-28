package com.hohomalls.web.mapper;

import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * BaseMapper.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 28/1/2022
 */
@Component
@Named("BaseMapper")
public class BaseMapper {

  @Named("jsonToStringMap")
  @SuppressWarnings("all")
  public Map jsonToStringMap(Object json) {
    return json == null ? null : (Map) json;
  }
}
