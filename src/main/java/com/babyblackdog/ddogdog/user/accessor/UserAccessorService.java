package com.babyblackdog.ddogdog.user.accessor;

import com.babyblackdog.ddogdog.common.point.Point;
import com.babyblackdog.ddogdog.user.service.dto.UserResult;

public interface UserAccessorService {

    UserResult findUserByEmail(String email);

    boolean doesUserExist(Long userId);

    boolean deductUserPoints(Long userId, Point point);

//  boolean doesUserExist(String email);
//
//  boolean deductUserPoints(String email, Point pointsToDeduct);

}
