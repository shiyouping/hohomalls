package com.hohomalls.data.initializer;

import com.hohomalls.data.document.Counter;
import com.hohomalls.data.util.DocUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import static com.hohomalls.data.document.Counter.Type;

/**
 * CounterInitializer.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 10/5/2022
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CounterInitializer implements ApplicationRunner {

  private final ReactiveMongoTemplate mongoTemplate;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    for (Type type : Type.values()) {
      this.initialize(type);
    }
  }

  @Transactional
  private void initialize(Type type) {
    this.mongoTemplate
        .findOne(new Query(Criteria.where("type").is(type.name())), Counter.class)
        .switchIfEmpty(
            Mono.defer(
                () -> {
                  var counter = new Counter(type, 1L);
                  DocUtil.updateDateTime(counter);
                  CounterInitializer.log.info("Initializing {}", counter);
                  return this.mongoTemplate.save(counter);
                }))
        .subscribe();
  }
}
