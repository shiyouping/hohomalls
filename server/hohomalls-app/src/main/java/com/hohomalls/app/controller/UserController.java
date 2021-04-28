package com.hohomalls.app.controller;

import com.hohomalls.app.document.User;
import com.hohomalls.app.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Mono;

/**
 * @author ricky.shiyouping@gmail.com
 * @since 25/4/2021
 */
@Controller
@RequestMapping(path = "/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping
  public @ResponseBody Mono<User> addKayak(@RequestBody User user) {
    return userService.save(user);
  }
}
