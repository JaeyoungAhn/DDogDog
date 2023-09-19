package com.babyblackdog.ddogdog.user.service.dto;

import com.babyblackdog.ddogdog.user.model.User;

public record UserResult(
        String email,
        String role,
        long point
) {

    public static UserResult of(User user) {
        return new UserResult(
                user.getEmail(),
                user.getRole(),
                user.getPointValue()
        );
    }
}
