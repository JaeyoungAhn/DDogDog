package com.babyblackdog.ddogdog.user.service;

import com.babyblackdog.ddogdog.common.point.Point;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public boolean doesUserExist(Long userId) {
        return false;
    }

    @Override
    public void debitPoint(Long userId, Point point) {
    }

    @Override
    public void creditPoint(long userId, Point point) {
    }
}
