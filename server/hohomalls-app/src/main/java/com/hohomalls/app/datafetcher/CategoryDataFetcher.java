package com.hohomalls.app.datafetcher;

import com.google.common.collect.Lists;
import com.hohomalls.app.document.Category;
import com.hohomalls.app.graphql.types.CategoryDto;
import com.hohomalls.app.mapper.CategoryMapper;
import com.hohomalls.app.service.CategoryService;
import com.hohomalls.web.aop.HasAnyRoles;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Stream;

import static com.hohomalls.core.enumeration.Role.*;

/**
 * CategoryDataFetcher.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 6/1/2022
 */
@DgsComponent
@RequiredArgsConstructor
public class CategoryDataFetcher {

  private final CategoryMapper categoryMapper;
  private final CategoryService categoryService;

  @DgsQuery
  @HasAnyRoles({ROLE_ANONYMOUS, ROLE_BUYER, ROLE_SELLER})
  public Mono<List<CategoryDto>> findAllCategories() {
    return this.categoryService
        .findAll()
        .collectList()
        .flatMap(
            categories -> {
              List<CategoryDto> tree = Lists.newArrayListWithCapacity(categories.size());
              this.getRoots(categories)
                  .forEach(
                      root -> {
                        this.buildCategoryTree(categories, root);
                        tree.add(root);
                      });
              return Mono.just(tree);
            });
  }

  private void buildCategoryTree(List<Category> categories, CategoryDto currentNode) {
    var ancestors = this.getAncestors(categories, currentNode.getId());
    if (CollectionUtils.isEmpty(ancestors)) {
      return;
    }

    ancestors.forEach(ancestor -> this.buildCategoryTree(categories, ancestor));
    currentNode.setAncestors(ancestors);
  }

  private List<CategoryDto> getAncestors(List<Category> categories, String parentId) {
    return categories.stream()
        .filter(category -> parentId.equals(category.getParentId()))
        .map(this.categoryMapper::toDto)
        .toList();
  }

  private Stream<CategoryDto> getRoots(List<Category> categories) {
    return categories.stream()
        .filter(category -> category.getParentId() == null)
        .map(this.categoryMapper::toDto);
  }
}