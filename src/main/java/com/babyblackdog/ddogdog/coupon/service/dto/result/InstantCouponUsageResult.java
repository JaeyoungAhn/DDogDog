package com.babyblackdog.ddogdog.coupon.service.dto.result;

import com.babyblackdog.ddogdog.coupon.domain.CouponUsage;
import com.babyblackdog.ddogdog.coupon.domain.vo.CouponUsageStatus;

public record InstantCouponUsageResult(CouponUsageStatus status) {

    public static InstantCouponUsageResult of(CouponUsage savedCouponUsage) {
        return new InstantCouponUsageResult(savedCouponUsage.getCouponUsageStatus());
    }
}
