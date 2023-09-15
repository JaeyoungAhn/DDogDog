package com.babyblackdog.ddogdog.global.config;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import com.babyblackdog.ddogdog.global.jwt.JwtAuthenticationFilter;
import com.babyblackdog.ddogdog.global.jwt.JwtAuthenticationProvider;
import com.babyblackdog.ddogdog.global.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.babyblackdog.ddogdog.global.oauth2.OAuth2AuthenticationSuccessHandler;
import com.babyblackdog.ddogdog.user.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final ApplicationContext applicationContext;
    private final JwtConfig jwtConfig;

    public SecurityConfig(ApplicationContext applicationContext, JwtConfig jwtConfig) {
        this.applicationContext = applicationContext;
        this.jwtConfig = jwtConfig;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(request -> request
                        .requestMatchers(
                                antMatcher("/login")).permitAll()
                        .anyRequest().authenticated())
                .csrf(AbstractHttpConfigurer::disable)
                .headers(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .rememberMe(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2Login(oauth2 -> oauth2
                        .authorizationEndpoint(endPoint -> endPoint
                                .authorizationRequestRepository(authorizationRequestRepository()))
                        .authorizedClientRepository(
                                applicationContext.getBean(OAuth2AuthorizedClientRepository.class))
                        .successHandler(oAuth2AuthenticationSuccessHandler(
                                jwtAuthenticationProvider(), applicationContext.getBean(UserService.class))))
                .addFilterBefore(
                        jwtAuthenticationFilter(jwtAuthenticationProvider()),
                        AnonymousAuthenticationFilter.class)
        ;
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers(
                        antMatcher("/assets/**")
                );
    }

    @Bean
    public JwtAuthenticationProvider jwtAuthenticationProvider() {
        return new JwtAuthenticationProvider(
                jwtConfig.getHeader(),
                jwtConfig.getIssuer(),
                jwtConfig.getClientSecret(),
                jwtConfig.getExpirySeconds()
        );
    }

    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtAuthenticationProvider jwtProvider) {
        return new JwtAuthenticationFilter(jwtProvider);
    }

    @Bean
    public AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }

    @Bean
    public OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler(
            JwtAuthenticationProvider jwtAuthenticationProvider, UserService userService
    ) {
        return new OAuth2AuthenticationSuccessHandler(jwtAuthenticationProvider, userService);
    }

}
