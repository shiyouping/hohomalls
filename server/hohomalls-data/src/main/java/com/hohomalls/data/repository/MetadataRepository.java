package com.hohomalls.data.repository;

import com.hohomalls.data.document.Metadata;
import reactor.core.publisher.Mono;

/**
 * MetadataRepository.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 9/1/2022
 */
public interface MetadataRepository extends BaseDocRepository<Metadata> {

  Mono<Metadata> findByNameAndPath(String name, String path);
}
