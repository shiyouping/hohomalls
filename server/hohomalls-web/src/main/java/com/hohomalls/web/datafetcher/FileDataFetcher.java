package com.hohomalls.web.datafetcher;

import com.hohomalls.core.common.Global;
import com.hohomalls.core.enumeration.Role;
import com.hohomalls.core.service.StorageService;
import com.hohomalls.core.util.HashUtil;
import com.hohomalls.data.document.Metadata;
import com.hohomalls.data.service.MetadataService;
import com.hohomalls.web.aop.HasAnyRoles;
import com.hohomalls.web.common.GraphqlType;
import com.hohomalls.web.config.WebProperties;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.exceptions.DgsBadRequestException;
import graphql.schema.DataFetchingEnvironment;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

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

  private final StorageService storageService;
  private final MetadataService metadataService;
  private final WebProperties webProperties;

  /**
   * The file upload of DGS Framework doesn't work with Spring WebFlux.
   *
   * <p>See the issue reported at https://github.com/Netflix/dgs-framework/issues/819
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

    if (!this.webProperties.multipart().contentTypes().contains(file.getContentType())) {
      return Mono.error(
          new DgsBadRequestException(
              "The content type of %s is not allowed".formatted(file.getContentType())));
    }

    var data = file.getBytes();
    var hash = HashUtil.getMurmur3(data);
    var fullPath = String.join(Global.SIGN_SLASH, rootDir, subDir);

    return this.metadataService
        .findByNameAndPath(hash, fullPath)
        .switchIfEmpty(Mono.defer(() -> this.saveFile(data, fullPath, hash)))
        .map(this::getFullUrl);
  }

  private String getFullUrl(Metadata metadata) {
    return String.join(
        Global.SIGN_SLASH,
        this.webProperties.multipart().baseStorageUrl(),
        metadata.getPath(),
        metadata.getName());
  }

  private Mono<Metadata> saveFile(byte[] data, String fullPath, String hash) {
    this.storageService.save(data, fullPath, hash);
    return this.metadataService.save(Metadata.builder().name(hash).path(fullPath).build());
  }
}
