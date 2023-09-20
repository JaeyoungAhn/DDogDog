package com.babyblackdog.ddogdog.coupon.controller.dto.response;

import com.babyblackdog.ddogdog.coupon.service.dto.InstantCouponResult;
import java.time.LocalDate;

public record InstantCouponResponse(Long couponId, Long roomId, String couponName, String couponType,
                                    String discountType, Double discountValue, LocalDate endDate) {

    public static InstantCouponResponse of(InstantCouponResult instantCouponResult) {
        return new InstantCouponResponse(instantCouponResult.couponId(), instantCouponResult.roomId(),
                instantCouponResult.couponName(), instantCouponResult.couponType(), instantCouponResult.discountType(),
                instantCouponResult.discountValue(), instantCouponResult.endDate());
    }
}
