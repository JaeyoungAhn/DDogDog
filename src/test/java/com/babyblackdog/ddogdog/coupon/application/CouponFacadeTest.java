package com.babyblackdog.ddogdog.coupon.application;

import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.babyblackdog.ddogdog.TestConfig;
import com.babyblackdog.ddogdog.common.auth.JwtSimpleAuthentication;
import com.babyblackdog.ddogdog.common.auth.Role;
import com.babyblackdog.ddogdog.coupon.domain.vo.CouponType;
import com.babyblackdog.ddogdog.coupon.domain.vo.DiscountType;
import com.babyblackdog.ddogdog.coupon.service.dto.command.InstantCouponCreationCommand;
import com.babyblackdog.ddogdog.coupon.service.dto.command.ManualCouponCreationCommand;
import com.babyblackdog.ddogdog.coupon.service.dto.result.InstantCouponCreationResult;
import com.babyblackdog.ddogdog.coupon.service.dto.result.ManualCouponCreationResult;
import com.babyblackdog.ddogdog.coupon.service.dto.result.ManualCouponGiftResult;
import com.babyblackdog.ddogdog.notification.NotificationService;
import com.babyblackdog.ddogdog.user.accessor.UserAccessorService;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest(classes = TestConfig.class, properties = {"spring.profiles.active=test"})
class CouponFacadeTest {

    @Autowired
    private TestConfig config;

    @Autowired
    private CouponFacade facade;

    @MockBean
    private UserAccessorService userAccessorService;

    @MockBean
    private NotificationService notificationService;

    @MockBean
    private JwtSimpleAuthentication jwtSimpleAuthentication;

    @BeforeEach
    void setUp() {
        when(jwtSimpleAuthentication.getEmail()).thenReturn(config.getUserOwnerEmail());
        System.out.println(config.getUserOwnerEmail().getValue());
        when(jwtSimpleAuthentication.getRole()).thenReturn(Role.OWNER);
    }

    @Test
    @DisplayName("수동 쿠폰을 정상적으로 등록한다.")
    void registerManualCoupon_createSuccess() {
        // Given
        ManualCouponCreationCommand command = new ManualCouponCreationCommand(
                "한정 수량 3000원 쿠폰",
                DiscountType.AMOUNT.name(),
                3_000.0,
                "ESDZXCQWEAI",
                50L,
                now(),
                now().plusMonths(1)
        );
        given(userAccessorService.isAdmin()).willReturn(true);
        doNothing().when(notificationService).notify(any(ManualCouponGiftResult.class));

        // When
        ManualCouponCreationResult result = facade.registerManualCoupon(command);

        // Then
        assertThat(result).isNotNull();
        assertThat(result).extracting("couponId").isNotNull();
        assertThat(result).extracting("couponName").isEqualTo(result.couponName());
        assertThat(result).extracting("couponType").isEqualTo(CouponType.MANUAL);
        assertThat(result).extracting("discountType").isEqualTo(result.discountType());
        assertThat(result).extracting("discountValue").isEqualTo(result.discountValue());
        assertThat(result).extracting("promoCode").isEqualTo(result.promoCode());
        assertThat(result).extracting("issueCount").isEqualTo(result.issueCount());
        assertThat(result).extracting("remainingCount").isEqualTo(result.issueCount());
        assertThat(result).extracting("startDate").isEqualTo(result.startDate());
        assertThat(result).extracting("endDate").isEqualTo(result.endDate());
    }

    @Test
    @DisplayName("즉시 할인 쿠폰을 정상적으로 등록한다.")
    void registerInstantCoupon_createSuccess() {
        // Given
        InstantCouponCreationCommand command = new InstantCouponCreationCommand(
                config.getRoomId(),
                "신라 호텔 10% 할인권",
                "percent",
                10.0,
                LocalDate.now(),
                LocalDate.now().plusMonths(1)
        );

        System.out.println(config.getRoomId());

        // When
        InstantCouponCreationResult result = facade.registerInstantCoupon(command);

        // Then
        assertThat(result).isNotNull();
        assertThat(result).extracting("couponId").isNotNull();
        assertThat(result).extracting("roomId").isEqualTo(result.roomId());
        assertThat(result).extracting("couponName").isEqualTo(result.couponName());
        assertThat(result).extracting("couponType").isEqualTo(result.couponType());
        assertThat(result).extracting("discountType").isEqualTo(result.discountType());
        assertThat(result).extracting("discountValue").isEqualTo(result.discountValue());
        assertThat(result).extracting("startDate").isEqualTo(result.startDate());
        assertThat(result).extracting("endDate").isEqualTo(result.endDate());
    }

}