package com.babyblackdog.ddogdog.coupon.controller.dto.response;

import com.babyblackdog.ddogdog.coupon.service.dto.result.InstantCouponCreationResult;
import java.time.LocalDate;

public record InstantCouponCreationResponse(Long couponId, Long roomId, String couponName, String couponType,
                                            String discountType, Double discountValue, LocalDate startDate,
                                            LocalDate endDate) {

    public static InstantCouponCreationResponse of(InstantCouponCreationResult instantCouponCreationResult) {
        return new InstantCouponCreationResponse(instantCouponCreationResult.couponId(),
                instantCouponCreationResult.roomId(), instantCouponCreationResult.couponName().getValue(),
                instantCouponCreationResult.couponType().name(), instantCouponCreationResult.discountType().name(),
                instantCouponCreationResult.discountValue(), instantCouponCreationResult.startDate(),
                instantCouponCreationResult.endDate());
    }
}
