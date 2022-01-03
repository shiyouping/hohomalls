package com.hohomalls.mongo.event;

import io.mongock.runner.spring.base.events.SpringMigrationSuccessEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * SuccessEventListener.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 3/1/2022
 */
@Slf4j
@Component
public class SuccessEventListener implements ApplicationListener<SpringMigrationSuccessEvent> {

  @Override
  public void onApplicationEvent(SpringMigrationSuccessEvent event) {
    SuccessEventListener.log.info("******************** Mongock succeeded. {}", event.toString());
  }
}
