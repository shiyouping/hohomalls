package com.hohomalls.data.repository;

import com.hohomalls.data.document.Counter;
import org.springframework.lang.NonNull;
import reactor.core.publisher.Mono;

import static com.hohomalls.data.document.Counter.Type;

/**
 * CounterRepository.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 10/5/2022
 */
public interface CounterRepository extends BaseDocRepository<Counter> {

  @NonNull
  Mono<Counter> findByType(@NonNull Type type);
}
