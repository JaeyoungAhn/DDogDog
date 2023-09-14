package com.babyblackdog.ddogdog.global.jwt;

import static io.micrometer.common.util.StringUtils.isNotEmpty;

import java.util.StringJoiner;
import org.springframework.util.Assert;

public class JwtAuthenticationPrincipal {

  private final String token;
  private final String username;
  private final String email;

  protected JwtAuthenticationPrincipal(String token, String username, String email) {
    Assert.isTrue(isNotEmpty(token), "token must be provided.");
    Assert.isTrue(isNotEmpty(username), "username must be provided.");
    Assert.isTrue(isNotEmpty(email), "email must be provided.");

    this.token = token;
    this.username = username;
    this.email = email;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", JwtAuthenticationPrincipal.class.getSimpleName() + "[", "]")
        .add("token='" + token + "'")
        .add("username='" + username + "'")
        .add("email='" + email + "'")
        .toString();
  }
}
