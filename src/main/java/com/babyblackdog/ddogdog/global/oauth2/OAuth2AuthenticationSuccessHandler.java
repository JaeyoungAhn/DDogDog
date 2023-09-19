package com.babyblackdog.ddogdog.global.oauth2;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.babyblackdog.ddogdog.global.jwt.Claims;
import com.babyblackdog.ddogdog.global.jwt.JwtAuthenticationProvider;
import com.babyblackdog.ddogdog.user.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.util.Assert;

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

            String email = parsePrincipal(principal);
            UserDetails userDetails = userService.loadUserByUsername(email);

            String loginSuccessJson = generateLoginSuccessJson(userDetails);
            response.setContentType(APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(UTF_8.name());
            response.setContentLength(loginSuccessJson.getBytes(UTF_8).length);
            response.getWriter().write(loginSuccessJson);
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }

    public String parsePrincipal(OAuth2User oAuth2User) {
        Assert.isTrue(oAuth2User != null, "oauth2User must be provided");

        Map<String, Object> attributes = oAuth2User.getAttributes();
        Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");

        return (String) account.get("email");
    }

    private String generateLoginSuccessJson(UserDetails userDetails) {
        String token = generateToken(userDetails);
        log.debug("Jwt({}) created for oauth2 login user {}", token, userDetails.getUsername());
        return """
                {
                  "token": "%s",
                  "email": "%s",
                  "role": "%s"
                }
                """.formatted(token,
                userDetails.getUsername(),
                userDetails.getAuthorities());
    }

    private String generateToken(UserDetails userDetails) {
        return jwtProvider.sign(
                Claims.from(userDetails.getUsername(), (List<GrantedAuthority>) userDetails.getAuthorities()));
    }

}
