package com.hohomalls.app.mapper;

import com.hohomalls.app.document.Item;
import com.hohomalls.app.graphql.types.CreateItemDto;
import com.hohomalls.app.graphql.types.ItemDto;
import com.hohomalls.app.graphql.types.UpdateItemDto;
import com.hohomalls.web.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * ItemMapper.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 28/12/2021
 */
@Mapper(uses = {BaseMapper.class})
public interface ItemMapper {

  @Mapping(target = "rating", ignore = true)
  @Mapping(
      target = "attributes",
      source = "attributes",
      qualifiedByName = {"BaseMapper", "jsonToStringMap"})
  Item toDoc(CreateItemDto dto);

  @Mapping(target = "shopId", ignore = true)
  @Mapping(target = "rating", ignore = true)
  @Mapping(
      target = "attributes",
      source = "attributes",
      qualifiedByName = {"BaseMapper", "jsonToStringMap"})
  Item toDoc(UpdateItemDto dto);

  ItemDto toDto(Item doc);
}
