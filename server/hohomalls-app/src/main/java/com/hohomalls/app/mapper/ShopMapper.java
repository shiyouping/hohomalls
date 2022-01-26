package com.hohomalls.app.mapper;

import com.hohomalls.app.document.Shop;
import com.hohomalls.app.graphql.types.CreateShopDto;
import com.hohomalls.app.graphql.types.ShopDto;
import com.hohomalls.app.graphql.types.UpdateShopDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * ShopMapper.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 28/12/2021
 */
@Mapper
public interface ShopMapper {

  @Mapping(target = "status", ignore = true)
  @Mapping(target = "sellerId", ignore = true)
  @Mapping(target = "rating", ignore = true)
  Shop toDoc(CreateShopDto dto);

  @Mapping(target = "sellerId", ignore = true)
  @Mapping(target = "rating", ignore = true)
  Shop toDoc(UpdateShopDto dto);

  ShopDto toDto(Shop doc);
}
