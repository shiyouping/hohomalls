package com.hohomalls.app.mapper;

import com.hohomalls.app.document.User;
import com.hohomalls.app.graphql.types.CreateUserDto;
import com.hohomalls.app.graphql.types.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * The interface of UserMapper.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 31/5/2021
 */
@Mapper
public interface UserMapper {

  @Mapping(target = "status", constant = "ACTIVE")
  User toDoc(CreateUserDto createUserDto);

  UserDto toDto(User user);
}
