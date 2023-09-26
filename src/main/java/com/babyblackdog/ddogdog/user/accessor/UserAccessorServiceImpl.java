package com.babyblackdog.ddogdog.user.accessor;

import static com.babyblackdog.ddogdog.global.exception.ErrorCode.USER_NOT_FOUND;

import com.babyblackdog.ddogdog.common.auth.JwtSimpleAuthentication;
import com.babyblackdog.ddogdog.common.auth.Role;
import com.babyblackdog.ddogdog.common.point.Point;
import com.babyblackdog.ddogdog.global.exception.UserException;
import com.babyblackdog.ddogdog.user.model.User;
import com.babyblackdog.ddogdog.user.repository.UserRepository;
import com.babyblackdog.ddogdog.user.service.dto.UserResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserAccessorServiceImpl implements
        UserAccessorService {

    private final UserRepository userRepository;
    private final JwtSimpleAuthentication authentication;

    public UserAccessorServiceImpl(UserRepository userRepository, JwtSimpleAuthentication authentication) {
        this.userRepository = userRepository;
        this.authentication = authentication;
    }

    @Override
    public UserResult findUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException(USER_NOT_FOUND));
        return UserResult.of(user);
    }

    @Transactional
    public void creditPoint(Point point) {
        getUser().addPoint(point);
    }

    @Transactional
    public void debitPoint(Point point) {
        getUser().subPoint(point);
    }

    @Override
    public boolean isAdmin() {
        return getUser().getRole() == Role.ADMIN;
    }

    @Override
    public boolean isOwner() {
        return getUser().getRole() == Role.OWNER;
    }

    private User getUser() {
        return userRepository.findByEmail(authentication.getEmailAddress())
                .orElseThrow(() -> new UserException(USER_NOT_FOUND));
    }
}
