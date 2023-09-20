package com.babyblackdog.ddogdog.coupon.controller.dto.response;

import com.babyblackdog.ddogdog.coupon.service.dto.ManualCouponUsageResult;

public record ManualCouponUsageResponse(String couponUsageStatus) {

    public static ManualCouponUsageResponse of(ManualCouponUsageResult manualCouponUsageResult) {
        return new ManualCouponUsageResponse(manualCouponUsageResult.status().name());
    }
}
