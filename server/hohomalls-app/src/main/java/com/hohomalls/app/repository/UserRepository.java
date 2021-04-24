package com.hohomalls.app.repository;

import com.hohomalls.app.document.User;
import com.hohomalls.mongo.repository.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * The repository of user document.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 24/4/2021
 */
@Repository
public interface UserRepository extends BaseRepository<User> {}
