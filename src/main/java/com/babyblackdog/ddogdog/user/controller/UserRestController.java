package com.babyblackdog.ddogdog.user.controller;

import com.babyblackdog.ddogdog.global.jwt.JwtAuthentication;
import com.babyblackdog.ddogdog.user.UserResult;
import com.babyblackdog.ddogdog.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  private final UserService service;

  public UserRestController(UserService service) {
    this.service = service;
  }

  @GetMapping(path = "/users/me")
  public String me(
      @AuthenticationPrincipal JwtAuthentication authentication) {
    UserResult userResult = service.findUserFromAuthentication(authentication);
    return SecurityContextHolder.getContext().getAuthentication().getName();
  }

  @GetMapping(path = "/users/info")
  public Authentication info() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    LoggerFactory.getLogger(getClass()).info("authentication : {}", authentication);
    return authentication;
  }

}