package com.babyblackdog.ddogdog.user.service;

import static io.micrometer.common.util.StringUtils.isNotEmpty;

import com.babyblackdog.ddogdog.common.point.Point;
import com.babyblackdog.ddogdog.global.jwt.JwtAuthentication;
import com.babyblackdog.ddogdog.user.UserResult;
import com.babyblackdog.ddogdog.user.model.Group;
import com.babyblackdog.ddogdog.user.model.User;
import com.babyblackdog.ddogdog.user.repository.GroupRepository;
import com.babyblackdog.ddogdog.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
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
  private final GroupRepository groupRepository;

  public UserService(UserRepository userRepository, GroupRepository groupRepository) {
    this.userRepository = userRepository;
    this.groupRepository = groupRepository;
  }

  @Transactional(readOnly = true)
  public UserResult findUserFromAuthentication(JwtAuthentication authentication) {
    Assert.notNull(authentication, "username must be provided");
    User user = userRepository.findByUsername(authentication.username)
        .orElseThrow(EntityNotFoundException::new);
    return UserResult.of(authentication.token, user);
  }

  @Transactional(readOnly = true)
  public Optional<User> findByProviderAndProviderId(String provider, String providerId) {
    Assert.isTrue(isNotEmpty(provider), "provider must be provided");
    Assert.isTrue(isNotEmpty(providerId), "providerId must be provided");

    return userRepository.findByProviderAndProviderId(provider, providerId);
  }

  @Transactional
  public User join(OAuth2User oAuth2User, String provider) {
    Assert.isTrue(oAuth2User != null, "oauth2User must be provided");
    Assert.isTrue(isNotEmpty(provider), "provider must be provided");

    String providerId = oAuth2User.getName();
    return findByProviderAndProviderId(provider, providerId)
        .map(user -> {
          log.warn("Already exists: {} for provider: {} providerId: {}", user, provider,
              providerId);
          return user;
        })
        .orElseGet(() -> {
          Map<String, Object> attributes = oAuth2User.getAttributes();
          Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
          Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
          Assert.isTrue(properties != null, "OAuth2User properties is empty");

          String nickname = (String) properties.get("nickname");
          String email = (String) account.get("email");
          Group group = groupRepository.findByName("USER_GROUP")
              .orElseThrow(
                  () -> new IllegalArgumentException("Could not found group for USER_GROUP"));
          return userRepository.save(
              new User(nickname, provider, providerId, email, group)
          );
        });
  }

  public boolean doesUserExist(Long userId) {
    return false;
  }

  public boolean deductUserPoints(Long userId, Point pointsToDeduct) {
    return false;
  }
}
