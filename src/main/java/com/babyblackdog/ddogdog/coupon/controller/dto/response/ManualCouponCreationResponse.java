package com.babyblackdog.ddogdog.coupon.controller.dto.response;

import com.babyblackdog.ddogdog.coupon.service.dto.ManualCouponCreationResult;
import java.time.LocalDate;

public record ManualCouponCreationResponse(Long couponId, String couponName, String couponType, String discountType,
                                           Double discountValue,
                                           String promoCode, Long issueCount, Long remainingCount, LocalDate startDate,
                                           LocalDate endDate) {

    public static ManualCouponCreationResponse of(ManualCouponCreationResult manualCouponCreationResult) {
        return new ManualCouponCreationResponse(manualCouponCreationResult.couponId(),
                manualCouponCreationResult.couponName(), manualCouponCreationResult.couponType(),
                manualCouponCreationResult.discountType(), manualCouponCreationResult.discountValue(),
                manualCouponCreationResult.promoCode(), manualCouponCreationResult.issueCount(),
                manualCouponCreationResult.remainingCount(), manualCouponCreationResult.startDate(),
                manualCouponCreationResult.endDate());
    }
}
