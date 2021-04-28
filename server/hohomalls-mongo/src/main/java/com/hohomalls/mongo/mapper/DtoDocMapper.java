package com.hohomalls.mongo.mapper;

import com.hohomalls.core.dto.BaseDto;
import com.hohomalls.mongo.document.Base;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The base mapper for all mappers.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 27/4/2021
 */
public interface DtoDocMapper<T extends BaseDto, D extends Base> {

  D toDoc(T dto);

  /** Maps a list of dto to a list of doc. */
  default List<D> toDocs(List<T> dtos) {
    if (CollectionUtils.isEmpty(dtos)) {
      return List.of();
    }

    return dtos.stream().map(this::toDoc).collect(Collectors.toList());
  }

  T toDto(D doc);

  /** Maps a list of doc to a list of dto. */
  default List<T> toDtos(List<D> docs) {
    if (CollectionUtils.isEmpty(docs)) {
      return List.of();
    }

    return docs.stream().map(this::toDto).collect(Collectors.toList());
  }
}
