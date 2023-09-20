package com.babyblackdog.ddogdog.coupon.controller.dto.response;

import com.babyblackdog.ddogdog.coupon.service.dto.ManualCouponResult;
import java.time.LocalDate;

public record ManualCouponResponse(Long couponUsageId, String couponName, String couponType, String discountType,
                                   Double discountValue, LocalDate endDate) {

    public static ManualCouponResponse of(ManualCouponResult manualCouponResult) {
        return new ManualCouponResponse(manualCouponResult.couponUsageId(), manualCouponResult.couponName(),
                manualCouponResult.couponType(), manualCouponResult.discountType(), manualCouponResult.discountValue(),
                manualCouponResult.endDate());
    }
}
