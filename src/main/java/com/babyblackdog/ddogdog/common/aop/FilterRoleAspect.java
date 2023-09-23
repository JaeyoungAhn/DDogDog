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
public class FilterRoleAspect {

    private final JwtSimpleAuthentication authentication;

    public FilterRoleAspect(JwtSimpleAuthentication authentication) {
        this.authentication = authentication;
    }

    @Pointcut("@annotation(com.babyblackdog.ddogdog.common.aop.FilterAdmin)")
    private void enableAdminFilter() {
    }

    @Pointcut("@annotation(com.babyblackdog.ddogdog.common.aop.FilterOwner)")
    private void enableOwnerFilter() {
    }

    @Before("enableAdminFilter()")
    public void filterAdminBefore() {
        if (authentication.getRole() != Role.USER) {
            throw new UserException(FORBIDDEN_ROLE);
        }
    }

    @Before("enableOwnerFilter()")
    public void filterOwnerBefore() {
        if (authentication.getRole() != Role.OWNER) {
            throw new UserException(FORBIDDEN_ROLE);
        }
    }

}
