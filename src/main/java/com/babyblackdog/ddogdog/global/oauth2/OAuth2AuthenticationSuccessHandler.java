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

      User user = processUserOAuth2UserJoin(principal);

      String loginSuccessJson = generateLoginSuccessJson(user);
      response.setContentType(APPLICATION_JSON_VALUE);
      response.setCharacterEncoding(UTF_8.name());
      response.setContentLength(loginSuccessJson.getBytes(UTF_8).length);
      response.getWriter().write(loginSuccessJson);
    } else {
      super.onAuthenticationSuccess(request, response, authentication);
    }
  }

  private User processUserOAuth2UserJoin(OAuth2User oAuth2User) {
    return userService.join(oAuth2User);
  }

  private String generateLoginSuccessJson(User user) {
    String token = generateToken(user);
    log.debug("Jwt({}) created for oauth2 login user {}", token, user.getUsername());
    return """
        {
          "token": "%s",
          "username": "%s",
          "email": "%s",
          "role": "%s",
          "point": "%d"
        }
        """.formatted(token, user.getUsername(), user.getEmail(), user.getRole(), user.getPoint());
  }

  private String generateToken(User user) {
    return jwtProvider.sign(
        Claims.from(user.getUsername(), user.getRole()));
  }

}
