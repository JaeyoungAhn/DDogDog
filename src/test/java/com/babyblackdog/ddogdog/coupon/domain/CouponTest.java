package com.babyblackdog.ddogdog.coupon.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.within;
import static org.assertj.core.api.BDDAssertions.catchException;

import com.babyblackdog.ddogdog.coupon.domain.vo.CouponName;
import com.babyblackdog.ddogdog.coupon.domain.vo.CouponPeriod;
import com.babyblackdog.ddogdog.coupon.domain.vo.CouponType;
import com.babyblackdog.ddogdog.coupon.domain.vo.DiscountType;
import com.babyblackdog.ddogdog.coupon.domain.vo.DiscountValue;
import com.babyblackdog.ddogdog.global.exception.CouponException;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CouponTest {

    @Test
    @DisplayName("유효한 퍼센트제 수동 할인 쿠폰을 등록한다.")
    void createManualPercentCoupon_createdSuccessfully() {
        // Given
        LocalDate now = LocalDate.now();

        // When
        Coupon coupon = new Coupon(
                new CouponName("50프로 할인 쿠폰"),
                DiscountValue.from(
                        DiscountType.PERCENT.name(),
                        50.0
                ),
                "EFNEZMXCMS",
                50L,
                new CouponPeriod(
                        now,
                        now.plusDays(7)
                )
        );

        // Then
        assertThat(coupon).isNotNull();
        assertThat(coupon.getCouponName().getValue()).isEqualTo("50프로 할인 쿠폰");
        assertThat(coupon.getCouponType()).isEqualTo(CouponType.MANUAL);
        assertThat(coupon.getDiscountValue().getType()).isEqualTo(DiscountType.PERCENT);
        assertThat(coupon.getDiscountValue().getValue()).isEqualTo(50.0, within(0.0001));
        assertThat(coupon.getPromoCode()).isEqualTo("EFNEZMXCMS");
        assertThat(coupon.getIssueCount()).isEqualTo(50L);
        assertThat(coupon.getRemainingCount()).isEqualTo(50L);
        assertThat(coupon.getStartDate()).isEqualTo(now);
        assertThat(coupon.getEndDate()).isEqualTo(now.plusDays(7));
    }

    @Test
    @DisplayName("유효한 고정 금액 수동 할인 쿠폰을 등록한다.")
    void createManualAmountCoupon_createdSuccessfully() {
        // Given
        LocalDate now = LocalDate.now();

        // When
        Coupon coupon = new Coupon(
                new CouponName("5000원 할인 쿠폰"),
                DiscountValue.from(
                        DiscountType.AMOUNT.name(),
                        5_000.0
                ),
                "EFNEZMXCMS",
                50L,
                new CouponPeriod(
                        now,
                        now.plusDays(7)
                )
        );

        // Then
        assertThat(coupon).isNotNull();
        assertThat(coupon.getCouponName().getValue()).isEqualTo("5000원 할인 쿠폰");
        assertThat(coupon.getCouponType()).isEqualTo(CouponType.MANUAL);
        assertThat(coupon.getDiscountValue().getType()).isEqualTo(DiscountType.AMOUNT);
        assertThat(coupon.getDiscountValue().getValue()).isEqualTo(5000.0, within(0.0001));
        assertThat(coupon.getPromoCode()).isEqualTo("EFNEZMXCMS");
        assertThat(coupon.getIssueCount()).isEqualTo(50L);
        assertThat(coupon.getRemainingCount()).isEqualTo(50L);
        assertThat(coupon.getStartDate()).isEqualTo(now);
        assertThat(coupon.getEndDate()).isEqualTo(now.plusDays(7));
    }

    @Test
    @DisplayName("유효한 퍼센트제 즉시 할인 쿠폰을 등록한다.")
    void createInstantPercentCoupon_createdSuccessfully() {
        // Given
        LocalDate now = LocalDate.now();

        // When
        Coupon coupon = new Coupon(
                1L,
                new CouponName("디럭스 룸 한정 30프로 할인 쿠폰"),
                DiscountValue.from(
                        DiscountType.PERCENT.name(),
                        30.0
                ),
                new CouponPeriod(
                        now,
                        now.plusDays(7)
                )
        );

        // Then
        assertThat(coupon).isNotNull();
        assertThat(coupon.getRoomId()).isEqualTo(1L);
        assertThat(coupon.getCouponName().getValue()).isEqualTo("디럭스 룸 한정 30프로 할인 쿠폰");
        assertThat(coupon.getCouponType()).isEqualTo(CouponType.INSTANT);
        assertThat(coupon.getDiscountValue().getType()).isEqualTo(DiscountType.PERCENT);
        assertThat(coupon.getDiscountValue().getValue()).isEqualTo(30.0, within(0.0001));
        assertThat(coupon.getStartDate()).isEqualTo(now);
        assertThat(coupon.getEndDate()).isEqualTo(now.plusDays(7));
    }

    @Test
    @DisplayName("유효한 고정 금액 즉시 할인 쿠폰을 등록한다.")
    void createInstantAmountCoupon_createdSuccessfully() {
        // Given
        LocalDate now = LocalDate.now();

        // When
        Coupon coupon = new Coupon(
                1L,
                new CouponName("디럭스 룸 한정 만원 할인 쿠폰"),
                DiscountValue.from(
                        DiscountType.AMOUNT.name(),
                        10_000.0
                ),
                new CouponPeriod(
                        now,
                        now.plusDays(7)
                )
        );

        // Then
        assertThat(coupon).isNotNull();
        assertThat(coupon.getRoomId()).isEqualTo(1L);
        assertThat(coupon.getCouponName().getValue()).isEqualTo("디럭스 룸 한정 만원 할인 쿠폰");
        assertThat(coupon.getCouponType()).isEqualTo(CouponType.INSTANT);
        assertThat(coupon.getDiscountValue().getType()).isEqualTo(DiscountType.AMOUNT);
        assertThat(coupon.getDiscountValue().getValue()).isEqualTo(10_000.0, within(0.0001));
        assertThat(coupon.getStartDate()).isEqualTo(now);
        assertThat(coupon.getEndDate()).isEqualTo(now.plusDays(7));
    }

    @Test
    @DisplayName("50자 이상의 쿠폰명을 가진 쿠폰을 생성할 수 없다.")
    void createCoupon_failOnExceededNameLength() {
        // Given && When
        LocalDate now = LocalDate.now();

        Exception exception = catchException(
                () -> new Coupon(
                        1L,
                        new CouponName(
                                "COUPON(ABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJ)"
                        ),
                        DiscountValue.from(
                                DiscountType.PERCENT.name(),
                                30.0
                        ),
                        new CouponPeriod(
                                now,
                                now.plusDays(7)
                        )
                )
        );

        // Then
        assertThat(exception).isInstanceOf(CouponException.class);
    }

    @Test
    @DisplayName("쿠폰 만료일이 시작일보다 앞선 쿠폰은 생성할 수 없다.")
    void createCoupon_failOnInvalidDateRange() {
        // Given && When
        LocalDate now = LocalDate.now();

        Exception exception = catchException(
                () -> new Coupon(
                        1L,
                        new CouponName(
                                "유효기간 설정이 잘못된 쿠폰"
                        ),
                        DiscountValue.from(
                                DiscountType.PERCENT.name(),
                                30.0
                        ),
                        new CouponPeriod(
                                now.plusDays(1),
                                now
                        )
                )
        );

        // Then
        assertThat(exception).isInstanceOf(CouponException.class);
    }

    @Test
    @DisplayName("쿠폰 유효기간이 한달 초과인 쿠폰은 생성할 수 없다.")
    void createReviewEntity_SuccessOnValid() {
        // Given && When
        LocalDate now = LocalDate.now();

        Exception exception = catchException(
                () -> new Coupon(
                        1L,
                        new CouponName(
                                "유효기간 설정이 잘못된 쿠폰"
                        ),
                        DiscountValue.from(
                                DiscountType.PERCENT.name(),
                                30.0
                        ),
                        new CouponPeriod(
                                now,
                                now.plusDays(31)
                        )
                )
        );

        // Then
        assertThat(exception).isInstanceOf(CouponException.class);
    }
}