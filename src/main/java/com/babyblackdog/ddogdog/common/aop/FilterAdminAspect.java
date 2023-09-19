package com.babyblackdog.ddogdog.common.aop;

import static com.babyblackdog.ddogdog.global.exception.ErrorCode.FORBIDDEN_ROLE;

import com.babyblackdog.ddogdog.common.auth.JwtSimpleAuthentication;
import com.babyblackdog.ddogdog.common.auth.Role;
import com.babyblackdog.ddogdog.global.exception.UserException;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class FilterAdminAspect {

    private final JwtSimpleAuthentication authentication;

    public FilterAdminAspect(JwtSimpleAuthentication authentication) {
        this.authentication = authentication;
    }

    @Pointcut("@annotation(com.babyblackdog.ddogdog.common.aop.FilterAdmin)")
    private void enableFilter() {
    }

    @Before("enableFilter()")
    public void before() {
        if (authentication.getRole() == Role.USER) {
            throw new UserException(FORBIDDEN_ROLE);
        }
    }

}
