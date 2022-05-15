package com.hohomalls.data.service;

import com.hohomalls.data.document.Counter;
import com.hohomalls.data.document.Counter.Type;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * CounterServiceImpl.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 15/5/2022
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CounterServiceImpl implements CounterService {

  private final ReactiveMongoOperations mongoOps;

  @Override
  public @NotNull Mono<Long> incrementAndGet(@NotNull Type type) {
    checkNotNull(type, "type cannot be null");

    return this.mongoOps
        .updateFirst(
            new Query(Criteria.where("type").is(type.name())),
            new Update().currentDate("updatedAt").inc("value", 1),
            Counter.class)
        .then(
            this.mongoOps
                .findOne(new Query(Criteria.where("type").is(type.name())), Counter.class)
                .map(Counter::getValue));
  }
}
