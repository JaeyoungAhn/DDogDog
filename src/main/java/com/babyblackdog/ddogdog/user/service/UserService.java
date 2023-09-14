package com.babyblackdog.ddogdog.user.service;

import com.babyblackdog.ddogdog.common.point.Point;

public interface UserService {

    /**
     * @param userId 존재하는지 검사할 유저 아이디
     * @return 존재하면 true, 존재하지 않는다면 false
     */
    boolean doesUserExist(Long userId);

    void debitPoint(Long userId, Point point);

    void creditPoint(long userId, Point point);
}
