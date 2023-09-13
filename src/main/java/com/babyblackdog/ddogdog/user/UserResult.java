package com.babyblackdog.ddogdog.user;

import com.babyblackdog.ddogdog.user.model.User;

public record UserResult(
    Long id,
    String token,
    String username,
    String provider,
    String providerId,
    String group,
    String email
) {

  public static UserResult of(String token, User user) {
    return new UserResult(
        user.getId(),
        token,
        user.getUsername(),
        user.getProvider(),
        user.getProviderId(),
        user.getGroup().getName(),
        user.getEmail()
    );
  }
}
