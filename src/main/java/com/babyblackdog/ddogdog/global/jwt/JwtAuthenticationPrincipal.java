package com.babyblackdog.ddogdog.global.jwt;

import static io.micrometer.common.util.StringUtils.isNotEmpty;

import org.springframework.util.Assert;

public record JwtAuthenticationPrincipal(String email) {

    public JwtAuthenticationPrincipal {
        Assert.isTrue(isNotEmpty(email), "email must be provided.");
    }
}
