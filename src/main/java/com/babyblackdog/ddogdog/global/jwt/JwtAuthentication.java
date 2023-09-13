package com.babyblackdog.ddogdog.global.jwt;

import static io.micrometer.common.util.StringUtils.isNotEmpty;

import java.util.StringJoiner;
import org.springframework.util.Assert;

public class JwtAuthentication {

  public final String token;
  public final String username;

  protected JwtAuthentication(String token, String username) {
    Assert.isTrue(isNotEmpty(token), "token must be provided.");
    Assert.isTrue(isNotEmpty(username), "username must be provided.");

    this.token = token;
    this.username = username;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", JwtAuthentication.class.getSimpleName() + "[", "]")
        .add("token='" + token + "'")
        .add("username='" + username + "'")
        .toString();
  }
}
