package com.hohomalls.app.mapper;

import com.hohomalls.app.document.Category;
import com.hohomalls.app.graphql.types.CategoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * CategoryMapper.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 28/12/2021
 */
@Mapper
public interface CategoryMapper {

  @Mapping(target = "ancestors", ignore = true)
  CategoryDto toDto(Category doc);
}
