package com.babyblackdog.ddogdog.coupon.controller.dto.response;

import com.babyblackdog.ddogdog.coupon.service.dto.InstantCouponUsageResult;

public record InstantCouponUsageResponse(String status) {

    public static InstantCouponUsageResponse of(InstantCouponUsageResult instantCouponUsageResult) {
        return new InstantCouponUsageResponse(instantCouponUsageResult.status().name());
    }
}
