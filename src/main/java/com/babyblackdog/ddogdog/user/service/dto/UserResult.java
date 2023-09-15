package com.babyblackdog.ddogdog.user.service.dto;

import com.babyblackdog.ddogdog.user.model.User;

public record UserResult(
        String token,
        String username,
        String email,
        String role,
        long point
) {

    public static UserResult of(User user) {
        return new UserResult(
                "COVERED_TOKEN",
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.getPoint()
        );
    }

    public static UserResult of(String token, User user) {
        return new UserResult(
                token,
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.getPoint()
        );
    }
}
