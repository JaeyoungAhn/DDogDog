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
  public boolean deductUserPoints(Long userId, Point pointsToDeduct) {
    return false;
  }
}
