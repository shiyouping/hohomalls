package com.hohomalls.app.mapper;

import com.hohomalls.app.document.Item;
import com.hohomalls.app.graphql.types.CreateItemDto;
import com.hohomalls.app.graphql.types.ItemDto;
import com.hohomalls.app.graphql.types.UpdateItemDto;
import com.hohomalls.web.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ItemMapper.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 28/12/2021
 */
@SuppressWarnings("PMD")
@Mapper(uses = {BaseMapper.class})
public interface ItemMapper {

  @Mapping(target = "rating", ignore = true)
  @Mapping(
      target = "attributes",
      source = "attributes",
      qualifiedByName = {"BaseMapper", "toStringMap"})
  Item toDoc(CreateItemDto dto);

  @Mapping(target = "shopId", ignore = true)
  @Mapping(target = "rating", ignore = true)
  @Mapping(
      target = "attributes",
      source = "attributes",
      qualifiedByName = {"BaseMapper", "toStringMap"})
  Item toDoc(UpdateItemDto dto);

  @Mapping(
      target = "updatedAt",
      source = "updatedAt",
      qualifiedByName = {"BaseMapper", "toOffsetDateTime"})
  ItemDto toDto(Item doc);

  default List<ItemDto> toDtos(List<Item> docs) {
    if (docs == null || docs.isEmpty()) {
      return null;
    }

    return docs.stream().map(this::toDto).collect(Collectors.toList());
  }
}
