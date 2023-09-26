package com.babyblackdog.ddogdog.common.auth;

import com.babyblackdog.ddogdog.global.jwt.JwtAuthenticationPrincipal;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class JwtSimpleAuthentication {

    public Email getEmail() {
        JwtAuthenticationPrincipal principal = (JwtAuthenticationPrincipal) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return new Email(principal.email());
    }

    public String getEmailAddress() {
        JwtAuthenticationPrincipal principal = (JwtAuthenticationPrincipal) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return principal.email();
    }

    public Role getRole() {
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities();
        return Role.valueOf(authorities.get(0)
                .getAuthority());
    }
}
