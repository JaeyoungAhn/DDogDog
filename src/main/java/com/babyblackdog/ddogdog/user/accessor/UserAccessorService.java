package com.babyblackdog.ddogdog.user.accessor;

import com.babyblackdog.ddogdog.common.point.Point;
import com.babyblackdog.ddogdog.user.service.dto.UserResult;

public interface UserAccessorService {

    UserResult findUserByEmail(String email);

    void creditPoint(Point point);

    void debitPoint(Point point);

    boolean isAdmin();

    boolean isOwner();

}
