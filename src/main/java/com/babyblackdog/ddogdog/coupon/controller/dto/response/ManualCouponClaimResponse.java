package com.babyblackdog.ddogdog.coupon.controller.dto.response;

import com.babyblackdog.ddogdog.coupon.service.dto.ManualCouponClaimResult;
import java.time.LocalDate;

public record ManualCouponClaimResponse(Long couponUsageId, String couponName, String couponType, String discountType,
                                        Double discountValue, LocalDate endDate) {

    public static ManualCouponClaimResponse of(ManualCouponClaimResult manualCouponClaimResult) {
        return new ManualCouponClaimResponse(manualCouponClaimResult.couponUsageId(),
                manualCouponClaimResult.couponName(),
                manualCouponClaimResult.couponType(), manualCouponClaimResult.discountType(),
                manualCouponClaimResult.discountValue(), manualCouponClaimResult.endDate());
    }
}
