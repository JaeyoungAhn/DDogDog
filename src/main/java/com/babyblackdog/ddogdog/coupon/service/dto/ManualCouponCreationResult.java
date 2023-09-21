package com.babyblackdog.ddogdog.coupon.service.dto;

import com.babyblackdog.ddogdog.coupon.domain.Coupon;
import java.time.LocalDate;

public record ManualCouponCreationResult(Long couponId, String couponName, String couponType, String discountType,
                                         Double discountValue,
                                         String promoCode, Long issueCount, Long remainingCount, LocalDate startDate,
                                         LocalDate endDate) {

    public static ManualCouponCreationResult of(Coupon savedCoupon) {
    }
}
