package com.babyblackdog.ddogdog.user.accessor;

import static com.babyblackdog.ddogdog.global.exception.ErrorCode.USER_NOT_FOUND;

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

  public UserAccessorServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserResult findUserByEmail(String email) {
    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new UserException(USER_NOT_FOUND));
    return UserResult.of(user);
  }

  @Override
  public boolean doesUserExist(Long userId) {
    return false;
  }

  @Override
  public boolean deductUserPoints(Long userId, Point point) {
    return false;
  }

//  @Override
//  public boolean doesUserExist(String email) {
//    return userRepository.existsByEmail(email);
//  }
//
//  @Override
//  public boolean deductUserPoints(String email, Point pointsToDeduct) {
//    UserResult userResult = findUserByEmail(email);
//    return userResult.point() >= pointsToDeduct.getValue();
//  }
}
