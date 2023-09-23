package com.babyblackdog.ddogdog.coupon.controller.dto.response;

import com.babyblackdog.ddogdog.coupon.service.dto.InstantCouponFindResult;
import java.time.LocalDate;

public record InstantCouponFindResponse(Long couponId, Long roomId, String couponName, String couponType,
                                        String discountType, Double discountValue, LocalDate endDate) {

    public static InstantCouponFindResponse of(InstantCouponFindResult instantCouponFindResult) {
        return new InstantCouponFindResponse(instantCouponFindResult.couponId(), instantCouponFindResult.roomId(),
                instantCouponFindResult.couponName(), instantCouponFindResult.couponType().name(),
                instantCouponFindResult.discountType().name(),
                instantCouponFindResult.discountValue(), instantCouponFindResult.endDate());
    }
}
