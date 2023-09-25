package com.babyblackdog.ddogdog.coupon.application;

import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

import com.babyblackdog.ddogdog.TestConfig;
import com.babyblackdog.ddogdog.common.auth.JwtSimpleAuthentication;
import com.babyblackdog.ddogdog.common.auth.Role;
import com.babyblackdog.ddogdog.common.point.Point;
import com.babyblackdog.ddogdog.coupon.domain.vo.CouponType;
import com.babyblackdog.ddogdog.coupon.domain.vo.DiscountType;
import com.babyblackdog.ddogdog.coupon.service.dto.command.InstantCouponCreationCommand;
import com.babyblackdog.ddogdog.coupon.service.dto.command.ManualCouponCreationCommand;
import com.babyblackdog.ddogdog.coupon.service.dto.result.InstantCouponCreationResult;
import com.babyblackdog.ddogdog.coupon.service.dto.result.InstantCouponFindResult;
import com.babyblackdog.ddogdog.coupon.service.dto.result.InstantCouponFindResults;
import com.babyblackdog.ddogdog.coupon.service.dto.result.ManualCouponClaimResult;
import com.babyblackdog.ddogdog.coupon.service.dto.result.ManualCouponCreationResult;
import com.babyblackdog.ddogdog.coupon.service.dto.result.ManualCouponFindResult;
import com.babyblackdog.ddogdog.coupon.service.dto.result.ManualCouponFindResults;
import com.babyblackdog.ddogdog.coupon.service.dto.result.ManualCouponGiftResult;
import com.babyblackdog.ddogdog.notification.NotificationService;
import com.babyblackdog.ddogdog.place.service.dto.RoomResult;
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
        given(jwtSimpleAuthentication.getEmail()).willReturn(config.getUserOwnerEmail());
        given(jwtSimpleAuthentication.getRole()).willReturn(Role.OWNER);
    }

    @Test
    @DisplayName("수동 쿠폰을 정상적으로 등록한다.")
    void registerManualCoupon_createSuccessfully() {
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
        assertThat(result.couponId()).isNotNull();
        assertThat(result.couponName().getValue()).isEqualTo(command.couponName());
        assertThat(result.couponType()).isEqualTo(CouponType.MANUAL);
        assertThat(result.discountType().name()).isEqualTo(command.discountType());
        assertThat(result.discountValue()).isEqualTo(command.discountValue());
        assertThat(result.promoCode()).isEqualTo(command.promoCode());
        assertThat(result.issueCount()).isEqualTo(command.issueCount());
        assertThat(result.remainingCount()).isEqualTo(command.issueCount());
        assertThat(result.startDate()).isEqualTo(command.startDate());
        assertThat(result.endDate()).isEqualTo(command.endDate());
    }

    @Test
    @DisplayName("즉시 할인 쿠폰을 정상적으로 등록한다.")
    void registerInstantCoupon_createSuccessfully() {
        // Given
        InstantCouponCreationCommand command = new InstantCouponCreationCommand(
                config.getRoom().id(),
                "신라 호텔 10% 할인권",
                "percent",
                10.0,
                LocalDate.now(),
                LocalDate.now().plusMonths(1)
        );

        // When
        InstantCouponCreationResult result = facade.registerInstantCoupon(command);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.couponId()).isNotNull();
        assertThat(result.roomId()).isEqualTo(command.roomId());
        assertThat(result.couponName().getValue()).isEqualTo(command.couponName());
        assertThat(result.couponType()).isEqualTo(CouponType.INSTANT);
        assertThat(result.discountType().name().toLowerCase()).isEqualTo(command.discountType().toLowerCase());
        assertThat(result.discountValue()).isEqualTo(command.discountValue());
        assertThat(result.startDate()).isEqualTo(command.startDate());
        assertThat(result.endDate()).isEqualTo(command.endDate());
    }

    @Test
    @DisplayName("등록된 즉시 할인 쿠폰을 삭제한다.")
    void deleteInstantCoupon_deleteSuccessfully() {
        // Given

        // When
        facade.deleteInstantCoupon(config.getInstantCoupon().couponId());
        InstantCouponFindResults results = facade.findAvailableInstantCouponsByHotelId(config.getHotelId());

        // Then
        assertThat(results.instantCouponResultPage()).isEmpty();
    }

    @Test
    @DisplayName("호텔이 제공하는 즉시 할인 쿠폰을 확인한다.")
    void findAvailableInstantCouponsByHotelId_foundSuccessfully() {
        // Given
        InstantCouponCreationResult createdInstantCoupon = config.getInstantCoupon();

        // When
        InstantCouponFindResults results = facade.findAvailableInstantCouponsByHotelId(config.getHotelId());
        InstantCouponFindResult result = results.instantCouponResultPage().get(0);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.couponId()).isNotNull();
        assertThat(result.roomId()).isEqualTo(createdInstantCoupon.roomId());
        assertThat(result.couponName()).isEqualTo(createdInstantCoupon.couponName().getValue());
        assertThat(result.couponType()).isEqualTo(createdInstantCoupon.couponType());
        assertThat(result.discountType()).isEqualTo(createdInstantCoupon.discountType());
        assertThat(result.discountValue()).isEqualTo(createdInstantCoupon.discountValue());
        assertThat(result.endDate()).isEqualTo(createdInstantCoupon.endDate());
    }

    @Test
    @DisplayName("프로모션 코드에 해당하는 수동 쿠폰을 수령한다.")
    void claimManualCoupon_claimedSuccessfully() {
        // Given
        ManualCouponCreationResult createdCoupon = config.getManualCoupon();

        // When
        ManualCouponClaimResult result = facade.claimManualCoupon(jwtSimpleAuthentication.getEmail(),
                createdCoupon.promoCode());

        // Then
        assertThat(result).isNotNull();
        assertThat(result.couponUsageId()).isNotNull();
        assertThat(result.couponName().getValue()).isEqualTo(createdCoupon.couponName().getValue());
        assertThat(result.couponType()).isEqualTo(createdCoupon.couponType());
        assertThat(result.discountType().name()).isEqualTo(createdCoupon.discountType().name());
        assertThat(result.discountValue()).isEqualTo(createdCoupon.discountValue());
        assertThat(result.endDate()).isEqualTo(createdCoupon.endDate());
    }

    @Test
    @DisplayName("본인 이메일에 해당하는 사용가능한 수동 쿠폰을 확인한다.")
    void findAvailableManualCouponsByEmail_foundSuccessfully() {
        // Given
        given(jwtSimpleAuthentication.getEmail()).willReturn(config.getUserEmail());
        given(jwtSimpleAuthentication.getRole()).willReturn(Role.USER);
        ManualCouponClaimResult claimedManualCoupon = config.getClaimedManualCoupon();

        // When
        ManualCouponFindResults results = facade.findAvailableManualCouponsByEmail(jwtSimpleAuthentication.getEmail());
        ManualCouponFindResult result = results.manualCouponResultPage().get(0);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.couponUsageId()).isNotNull();
        assertThat(result.couponName().getValue()).isEqualTo(claimedManualCoupon.couponName().getValue());
        assertThat(result.couponType()).isEqualTo(claimedManualCoupon.couponType());
        assertThat(result.discountType().name()).isEqualTo(claimedManualCoupon.discountType().name());
        assertThat(result.discountValue()).isEqualTo(claimedManualCoupon.discountValue());
        assertThat(result.endDate()).isEqualTo(claimedManualCoupon.endDate());
    }

    @Test
    @DisplayName("고정 액수의 수동 사용 쿠폰 적용을 통해 할인 받는 액수를 확인한다.")
    void calculateDiscountAmountForManualCoupon_returnDiscountAmount() {
        // Given
        given(jwtSimpleAuthentication.getEmail()).willReturn(config.getUserEmail());
        given(jwtSimpleAuthentication.getRole()).willReturn(Role.USER);
        RoomResult room = config.getRoom();
        ManualCouponClaimResult claimedManualCoupon = config.getClaimedManualCoupon();
        Long discountValue = claimedManualCoupon.discountValue().longValue(); // AMOUNT:3000.0

        // When
        Point point = facade.calculateDiscountAmount(new Point(room.point()),
                claimedManualCoupon.couponUsageId(), CouponType.MANUAL.name());

        // Then
        assertThat(point).isNotNull();
        assertThat(point.getValue()).isEqualTo(discountValue);
    }

    @Test
    @DisplayName("퍼센트 단위의 즉시 쿠폰 적용을 통해 할인 받는 액수를 확인한다.")
    void calculateDiscountAmountForInstantCoupon_returnDiscountAmount() {
        // Given
//        given(jwtSimpleAuthentication.getEmail()).willReturn(config.getUserEmail());
//        given(jwtSimpleAuthentication.getRole()).willReturn(Role.USER);
        RoomResult room = config.getRoom();
        InstantCouponCreationResult createdInstantCoupon = config.getInstantCoupon();
        long discountValue = createdInstantCoupon.discountValue().longValue(); // PERCENT:100%

        // When
        Point point = facade.calculateDiscountAmount(new Point(room.point()),
                createdInstantCoupon.couponId(), CouponType.INSTANT.name());

        // Then
        assertThat(point).isNotNull();
        assertThat(point.getValue()).isEqualTo(room.point() * (discountValue / 100));
    }

}