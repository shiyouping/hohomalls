package com.hohomalls.web.datafetcher;

import com.hohomalls.core.enumeration.Role;
import com.hohomalls.web.aop.HasAnyRoles;
import com.hohomalls.web.common.GraphqlType;
import com.hohomalls.web.service.FileService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.exceptions.DgsBadRequestException;
import graphql.schema.DataFetchingEnvironment;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * FileDataFetcher.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 9/1/2022
 */
@Slf4j
@DgsComponent
@AllArgsConstructor
public class FileDataFetcher {

  private final FileService fileService;

  /**
   * The file upload of DGS Framework doesn't work with Spring WebFlux.
   *
   * <p>See the issue reported at https://github.com/Netflix/dgs-framework/issues/819 .
   */
  @SneakyThrows
  @HasAnyRoles({Role.ROLE_BUYER, Role.ROLE_SELLER})
  @DgsData(parentType = GraphqlType.MUTATION)
  public Mono<String> uploadFile(DataFetchingEnvironment env) {
    FileDataFetcher.log.info("Received a request to upload a file");

    // NOTE: Cannot use @InputArgument or Object Mapper to convert
    // to class, because MultipartFile cannot be deserialized
    MultipartFile file = env.getArgument("file");
    String rootDir = env.getArgument("rootDir");
    String subDir = env.getArgument("subDir");

    if (file.isEmpty() || file.getSize() == 0) {
      return Mono.error(new DgsBadRequestException("Cannot save an empty file"));
    }

    if (!StringUtils.hasText(rootDir) || !StringUtils.hasText(subDir)) {
      return Mono.error(new DgsBadRequestException("Invalid rootDir or subDir"));
    }

    this.fileService.validate(
        file.getSize(), MediaType.valueOf(Objects.requireNonNull(file.getContentType())));

    return this.fileService.save(rootDir, subDir, file.getName(), file.getBytes());
  }
}
