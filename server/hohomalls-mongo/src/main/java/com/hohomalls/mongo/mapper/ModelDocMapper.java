package com.hohomalls.mongo.mapper;

import com.hohomalls.core.model.BaseModel;
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
public interface ModelDocMapper<M extends BaseModel, D extends Base> {

  D toDoc(M model);

  /** Maps a list of model to a list of doc. */
  default List<D> toDocs(List<M> models) {
    if (CollectionUtils.isEmpty(models)) {
      return List.of();
    }

    return models.stream().map(this::toDoc).collect(Collectors.toList());
  }

  M toModel(D doc);

  /** Maps a list of doc to a list of model. */
  default List<M> toModels(List<D> docs) {
    if (CollectionUtils.isEmpty(docs)) {
      return List.of();
    }

    return docs.stream().map(this::toModel).collect(Collectors.toList());
  }
}
