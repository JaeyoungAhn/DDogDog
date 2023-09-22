package com.babyblackdog.ddogdog.util;

import com.babyblackdog.ddogdog.global.jwt.JwtAuthenticationPrincipal;
import com.babyblackdog.ddogdog.global.jwt.JwtAuthenticationToken;
import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockCustomUserSecurityContextFactory implements
        WithSecurityContextFactory<WithMockCustomUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser mockCustomUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        JwtAuthenticationToken authentication =
                new JwtAuthenticationToken(
                        new JwtAuthenticationPrincipal(
                                mockCustomUser.email()),
                        null,
                        List.of(new SimpleGrantedAuthority(mockCustomUser.role())));
        context.setAuthentication(authentication);
        return context;
    }
}
