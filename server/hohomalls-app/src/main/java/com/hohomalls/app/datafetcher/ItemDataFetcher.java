package com.hohomalls.app.datafetcher;

import com.hohomalls.app.graphql.types.CreateItemDto;
import com.hohomalls.app.graphql.types.ItemDto;
import com.hohomalls.app.graphql.types.UpdateItemDto;
import com.hohomalls.app.mapper.ItemMapper;
import com.hohomalls.app.service.ItemService;
import com.hohomalls.app.service.ShopService;
import com.hohomalls.core.enumeration.Role;
import com.hohomalls.core.exception.InvalidTokenException;
import com.hohomalls.web.aop.HasAnyRoles;
import com.hohomalls.web.service.TokenService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestHeader;
import reactor.core.publisher.Mono;

/**
 * ItemDataFetcher.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 28/12/2021
 */
@Slf4j
@DgsComponent
@RequiredArgsConstructor
public class ItemDataFetcher {

  private final ItemMapper itemMapper;
  private final ItemService itemService;
  private final ShopService shopService;
  private final TokenService tokenService;

  @DgsMutation
  @HasAnyRoles({Role.ROLE_SELLER})
  public Mono<ItemDto> createItem(
      @RequestHeader String authorization, @InputArgument("item") CreateItemDto createItemDto) {
    ItemDataFetcher.log.info(
        "Received a request to create an item. Authorization={}, CreateItemDto={}",
        authorization,
        createItemDto);

    var userId = this.tokenService.getUserIdFromAuth(authorization);
    if (userId.isEmpty()) {
      return Mono.error(new InvalidTokenException("User ID is missing"));
    }

    return this.itemService
        .save(this.itemMapper.toDoc(createItemDto), userId.get())
        .map(this.itemMapper::toDto);
  }

  @DgsQuery
  @HasAnyRoles({Role.ROLE_SELLER})
  public Mono<ItemDto> findItem(
      @RequestHeader String authorization, @InputArgument("id") String id) {
    return Mono.empty();
  }

  @DgsMutation
  @HasAnyRoles({Role.ROLE_SELLER})
  public Mono<ItemDto> updateItem(
      @RequestHeader String authorization, @InputArgument("item") UpdateItemDto updateItemDto) {
    return Mono.empty();
  }
}
