package com.hohomalls.app.repository;

import com.hohomalls.app.document.Order;
import com.hohomalls.data.repository.BaseDocRepository;
import org.springframework.lang.NonNull;
import reactor.core.publisher.Mono;

/**
 * The repository of Order documents.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 24/4/2021
 */
public interface OrderRepository extends BaseDocRepository<Order> {

  @NonNull
  Mono<Order> findByNumber(@NonNull String number);
}
