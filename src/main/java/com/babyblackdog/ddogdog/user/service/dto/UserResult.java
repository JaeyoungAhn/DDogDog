package com.babyblackdog.ddogdog.user.service.dto;

import com.babyblackdog.ddogdog.user.model.User;

public record UserResult(
    Long id,
    String token,
    String username,
    String email,
    String role,
    long point
) {

  public static UserResult of(User user) {
    return new UserResult(
        user.getId(),
        "COVERED_TOKEN",
        user.getUsername(),
        user.getEmail(),
        user.getRole(),
        user.getPoint()
    );
  }

  public static UserResult of(String token, User user) {
    return new UserResult(
        user.getId(),
        token,
        user.getUsername(),
        user.getEmail(),
        user.getRole(),
        user.getPoint()
    );
  }
}
