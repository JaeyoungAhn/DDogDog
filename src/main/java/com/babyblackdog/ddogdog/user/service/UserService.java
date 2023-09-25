package com.babyblackdog.ddogdog.user.service;

import static com.babyblackdog.ddogdog.global.exception.ErrorCode.USER_NOT_FOUND;

import com.babyblackdog.ddogdog.common.auth.Email;
import com.babyblackdog.ddogdog.common.auth.JwtSimpleAuthentication;
import com.babyblackdog.ddogdog.common.auth.Role;
import com.babyblackdog.ddogdog.common.point.Point;
import com.babyblackdog.ddogdog.global.exception.UserException;
import com.babyblackdog.ddogdog.user.model.User;
import com.babyblackdog.ddogdog.user.model.UserDetailsImpl;
import com.babyblackdog.ddogdog.user.repository.UserRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User userEntity = this.findByEmail(email)
                .map(user -> {
                    log.warn("Already exists user: {}", user);
                    return user;
                })
                .orElseGet(() -> userRepository.save(
                        new User(email, Role.USER, new Point(0))));
        return new UserDetailsImpl(userEntity);
    }

    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public void chargePoint(String email, Point charge) {
        userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException(USER_NOT_FOUND))
                .addPoint(charge);
    }

    @Transactional
    public void save(User newUser) {
        userRepository.save(newUser);
    }

}
