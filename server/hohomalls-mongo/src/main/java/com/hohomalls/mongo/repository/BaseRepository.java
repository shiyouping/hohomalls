package com.hohomalls.mongo.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * The parent mongo repository of all other repositories.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 24/4/2021
 */
@NoRepositoryBean
public interface BaseRepository<D> extends ReactiveMongoRepository<D, String> {}
