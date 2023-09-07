package com.babyblackdog.ddogdog.user.service;

import com.babyblackdog.ddogdog.common.point.Point;

public interface UserService {

  /**
   * @param userId 존재하는지 검사할 유저 아이디
   * @return 존재하면 true, 존재하지 않는다면 false
   */
  boolean doesUserExist(Long userId);

  /**
   * @param userId         포인트를 삭감할 아이디
   * @param pointsToDeduct 삭감할 포인트
   * @return 삭감하였다면 true, 삭감하지 못헀다면 false
   */
  boolean deductUserPoints(Long userId, Point pointsToDeduct);
}
