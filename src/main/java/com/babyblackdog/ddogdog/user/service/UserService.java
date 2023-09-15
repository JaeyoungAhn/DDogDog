package com.babyblackdog.ddogdog.user.service;

import static com.babyblackdog.ddogdog.global.exception.ErrorCode.USER_NOT_FOUND;

import com.babyblackdog.ddogdog.common.point.Point;
import com.babyblackdog.ddogdog.global.exception.UserException;
import com.babyblackdog.ddogdog.user.model.Role;
import com.babyblackdog.ddogdog.user.model.User;
import com.babyblackdog.ddogdog.user.repository.UserRepository;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@Transactional(readOnly = true)
public class UserService {

  private final Logger log = LoggerFactory.getLogger(getClass());

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Transactional(readOnly = true)
  public Optional<User> findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  @Transactional
  public User join(OAuth2User oAuth2User) {
    Assert.isTrue(oAuth2User != null, "oauth2User must be provided");

    Map<String, Object> attributes = oAuth2User.getAttributes();
    Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
    Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");

    Assert.isTrue(properties != null, "OAuth2User properties is empty");
    Assert.isTrue(account != null, "OAuth2User account is empty");

    String nickname = (String) properties.get("nickname");
    String email = (String) account.get("email");
    return this.findByEmail(email)
        .map(user -> {
          log.warn("Already exists user: {}", user);
          return user;
        })
        .orElseGet(() -> userRepository.save(
            new User(nickname, email, Role.USER, new Point(0))));
  }

  @Transactional
  public void chargePoint(String email, long charge) {
    userRepository.findByEmail(email)
        .orElseThrow(() -> new UserException(USER_NOT_FOUND))
        .addPoint(charge);
  }
}
