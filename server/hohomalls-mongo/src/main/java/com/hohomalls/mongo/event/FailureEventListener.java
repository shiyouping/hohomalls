package com.hohomalls.mongo.event;

import io.mongock.runner.spring.base.events.SpringMigrationFailureEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * FailureEventListener.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 3/1/2022
 */
@Slf4j
@Component
public class FailureEventListener implements ApplicationListener<SpringMigrationFailureEvent> {

  @Override
  public void onApplicationEvent(SpringMigrationFailureEvent event) {
    FailureEventListener.log.error("******************** Mongock failed. {}", event.toString());
  }
}
