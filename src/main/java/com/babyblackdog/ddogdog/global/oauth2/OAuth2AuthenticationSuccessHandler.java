package com.babyblackdog.ddogdog.global.oauth2;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.babyblackdog.ddogdog.global.jwt.Claims;
import com.babyblackdog.ddogdog.global.jwt.JwtAuthenticationProvider;
import com.babyblackdog.ddogdog.user.model.User;
import com.babyblackdog.ddogdog.user.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class OAuth2AuthenticationSuccessHandler extends
    SavedRequestAwareAuthenticationSuccessHandler {

  private final Logger log = LoggerFactory.getLogger(getClass());

  private final JwtAuthenticationProvider jwtProvider;

  private final UserService userService;

  public OAuth2AuthenticationSuccessHandler(JwtAuthenticationProvider jwtProvider,
      UserService userService) {
    this.jwtProvider = jwtProvider;
    this.userService = userService;
  }

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws ServletException, IOException {
    if (authentication instanceof OAuth2AuthenticationToken) {
      OAuth2AuthenticationToken oauth2Token = (OAuth2AuthenticationToken) authentication;
      OAuth2User principal = oauth2Token.getPrincipal();
      String registrationId = oauth2Token.getAuthorizedClientRegistrationId();

      User user = processUserOAuth2UserJoin(principal, registrationId);

      String loginSuccessJson = generateLoginSuccessJson(user);
      response.setContentType(APPLICATION_JSON_VALUE);
      response.setCharacterEncoding(UTF_8.name());
      response.setContentLength(loginSuccessJson.getBytes(UTF_8).length);
      response.getWriter().write(loginSuccessJson);
    } else {
      super.onAuthenticationSuccess(request, response, authentication);
    }
  }

  private User processUserOAuth2UserJoin(OAuth2User oAuth2User, String registrationId) {
    return userService.join(oAuth2User, registrationId);
  }

  private String generateLoginSuccessJson(User user) {
    String token = generateToken(user);
    log.debug("Jwt({}) created for oauth2 login user {}", token, user.getUsername());
    return """
        {
          "token": "%s",
          "username": "%s",
          "provider": "%s",
          "group": "%s",
          "email": "%s",
        }
        """.formatted(token, user.getUsername(), user.getProvider(),
        user.getGroup().getName(), user.getEmail());
  }

  private String generateToken(User user) {
    String[] roles = user.getGroup().getAuthorities()
        .stream()
        .map(SimpleGrantedAuthority::getAuthority)
        .toArray(String[]::new);
    return jwtProvider.sign(
        Claims.from(user.getUsername(), roles));
  }

}
