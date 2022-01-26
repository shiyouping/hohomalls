package com.hohomalls.app.datafetcher;

import com.hohomalls.app.graphql.types.CreateShopDto;
import com.hohomalls.app.graphql.types.ShopDto;
import com.hohomalls.app.graphql.types.UpdateShopDto;
import com.hohomalls.app.mapper.ShopMapper;
import com.hohomalls.app.service.ShopService;
import com.hohomalls.core.enumeration.Role;
import com.hohomalls.core.exception.InvalidTokenException;
import com.hohomalls.core.util.BeanUtil;
import com.hohomalls.web.aop.HasAnyRoles;
import com.hohomalls.web.service.TokenService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.netflix.graphql.dgs.exceptions.DgsBadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestHeader;
import reactor.core.publisher.Mono;

import static com.hohomalls.app.document.Shop.ShopStatus.OPEN;

/**
 * ShopDataFetcher.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 26/1/2022
 */
@Slf4j
@DgsComponent
@RequiredArgsConstructor
public class ShopDataFetcher {

  private final ShopMapper shopMapper;
  private final ShopService shopService;
  private final TokenService tokenService;

  @DgsMutation
  @HasAnyRoles({Role.ROLE_SELLER})
  public Mono<ShopDto> createShop(
      @RequestHeader String authorization, @InputArgument("shop") CreateShopDto createShopDto) {
    ShopDataFetcher.log.info(
        "Received a request to create a shop. Authorization={}, CreateShopDto={}",
        authorization,
        createShopDto);

    var userId = this.tokenService.getUserIdFromAuth(authorization);
    if (userId.isEmpty()) {
      return Mono.error(new InvalidTokenException("User ID is missing"));
    }

    var doc = this.shopMapper.toDoc(createShopDto);
    doc.setSellerId(userId.get());
    doc.setStatus(OPEN);

    return this.shopService.save(doc).map(this.shopMapper::toDto);
  }

  @DgsQuery
  @HasAnyRoles({Role.ROLE_SELLER})
  public Mono<ShopDto> findShop(
      @RequestHeader String authorization, @InputArgument("name") String name) {
    ShopDataFetcher.log.info(
        "Received a request to find a shop. Authorization={}, name={}", authorization, name);

    var userId = this.tokenService.getUserIdFromAuth(authorization);
    if (userId.isEmpty()) {
      return Mono.error(new InvalidTokenException("User ID is missing"));
    }

    return this.shopService
        .findByName(name)
        .switchIfEmpty(Mono.error(new DgsBadRequestException("Invalid name %s".formatted(name))))
        .map(
            shop -> {
              if (!userId.get().equals(shop.getSellerId())) {
                throw new DgsBadRequestException(
                    "Current user doesn't have the shop with name %s".formatted(name));
              }

              return this.shopMapper.toDto(shop);
            });
  }

  @DgsMutation
  @HasAnyRoles({Role.ROLE_SELLER})
  public Mono<ShopDto> updateShop(
      @RequestHeader String authorization, @InputArgument("shop") UpdateShopDto updateShopDto) {
    ShopDataFetcher.log.info(
        "Received a request to update a shop. Authorization={}, UpdateShopDto={}",
        authorization,
        updateShopDto);

    return this.shopService
        .findById(updateShopDto.getId())
        .switchIfEmpty(
            Mono.error(
                new DgsBadRequestException("Invalid shop id %s".formatted(updateShopDto.getId()))))
        .flatMap(
            oldShop -> {
              var newShop = this.shopMapper.toDoc(updateShopDto);
              BeanUtil.copyNonnullProperties(oldShop, newShop);
              return this.shopService.save(oldShop).map(this.shopMapper::toDto);
            });
  }
}
