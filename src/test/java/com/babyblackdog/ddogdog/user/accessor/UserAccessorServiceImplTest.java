package com.babyblackdog.ddogdog.user.accessor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.babyblackdog.ddogdog.WithMockCustomUser;
import com.babyblackdog.ddogdog.common.auth.Role;
import com.babyblackdog.ddogdog.common.point.Point;
import com.babyblackdog.ddogdog.global.exception.PointException;
import com.babyblackdog.ddogdog.user.model.User;
import com.babyblackdog.ddogdog.user.repository.UserRepository;
import com.babyblackdog.ddogdog.user.service.dto.UserResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class UserAccessorServiceImplTest {

    @Autowired
    private UserAccessorService userAccessorService;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        User entity = new User(
                "danaka@naver.com",
                Role.USER,
                new Point(2_000L)
        );
        user = userRepository.save(entity);
    }

    @Test
    @DisplayName("이메일로 유저를 찾는다.")
    void findUserByEmail_Success() {
        // Given
        String email = user.getEmail();

        // When
        UserResult userResult = userAccessorService.findUserByEmail(email);

        // Then
        assertThat(userResult.point()).isEqualTo(user.getPointValue());
    }

    @Test
    @DisplayName("유저에게 포인트를 지급한다.")
    @WithMockCustomUser
    void creditPoint_Success() {
        // Given
        long origin = user.getPointValue();
        Point point = new Point(1_000L);

        // When
        userAccessorService.creditPoint(point);

        // Then
        UserResult userResult = userAccessorService.findUserByEmail(user.getEmail());

        assertThat(userResult.point()).isEqualTo(origin + point.getValue());
    }

    @Test
    @DisplayName("유저에게 포인트를 차감한다.")
    @WithMockCustomUser
    void debitPoint_Success() {
        // Given
        long origin = user.getPointValue();
        Point point = new Point(1_000L);

        // When
        userAccessorService.debitPoint(point);

        // Then
        UserResult userResult = userAccessorService.findUserByEmail(user.getEmail());

        assertThat(userResult.point()).isEqualTo(origin - point.getValue());
    }

    @Test
    @DisplayName("유저에게 차감할 수 없는 포인트를 차감하는 경우 실패한다.")
    @WithMockCustomUser
    void debitPoint_Fail() {
        // Given
        Point point = new Point(4_000L);

        // When & Then
        assertThatThrownBy(() -> userAccessorService.debitPoint(point))
                .isInstanceOf(PointException.class);
    }
}