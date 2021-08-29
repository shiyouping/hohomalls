package com.hohomalls;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import reactor.core.publisher.Hooks;
import reactor.tools.agent.ReactorDebugAgent;

import java.util.Arrays;

import static com.hohomalls.core.common.Global.PROFILE_PROD;

/**
 * The main entry of this application.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 11/4/2021
 */
@Slf4j
@SpringBootApplication
public class Application { // NOPMD

  /** The entry of this application. */
  public static void main(String[] args) {
    var app = new SpringApplication(Application.class);
    addListeners(app);
    app.run(args);
  }

  private static void addListeners(SpringApplication app) {
    app.addListeners(
        (ApplicationListener<ApplicationEnvironmentPreparedEvent>)
            event -> {
              var isProduction =
                  Arrays.stream(event.getEnvironment().getActiveProfiles())
                      .anyMatch(PROFILE_PROD::equalsIgnoreCase);

              final var debugInfo = "***** Activate Reactor's Global Debug *****";

              if (isProduction) {
                ReactorDebugAgent.init();
                log.info(debugInfo);
                return;
              }

              Hooks.onOperatorDebug();
              log.info(debugInfo);
            });
  }
}
