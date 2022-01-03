package com.hohomalls.mongo.event;

import io.mongock.runner.spring.base.events.SpringMigrationStartedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * StartedEventListener.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 3/1/2022
 */
@Slf4j
@Component
public class StartedEventListener implements ApplicationListener<SpringMigrationStartedEvent> {

  @Override
  public void onApplicationEvent(SpringMigrationStartedEvent event) {
    StartedEventListener.log.info("******************** Mongock started. {}", event.toString());
  }
}
