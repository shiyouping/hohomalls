package com.hohomalls.data.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * The parent data repository of all other repositories.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 24/4/2021
 */
@NoRepositoryBean
public interface BaseDocRepository<D> extends ReactiveMongoRepository<D, String> {}
