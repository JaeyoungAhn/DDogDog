package com.babyblackdog.ddogdog.user.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.babyblackdog.ddogdog.common.auth.Role;
import com.babyblackdog.ddogdog.common.point.Point;
import com.babyblackdog.ddogdog.user.model.User;
import com.babyblackdog.ddogdog.user.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private User savedUser;

    @BeforeEach
    void setUp() {
        savedUser = userRepository.save(
                new User(
                        "danaka@naver.com",
                        Role.USER,
                        new Point(0)
                )
        );
    }

    @Test
    @DisplayName("유저에게 포인트를 추가한다.")
    void chargePoint_Success() {
        // Given
        String email = savedUser.getEmail();
        Point charge = new Point(2_000L);

        // When
        userService.chargePoint(email, charge);

        // Then
        Optional<User> user = userService.findByEmail(email);

        assertThat(user).isPresent();
        assertThat(user.get().getPoint()).isEqualTo(charge);
    }

}