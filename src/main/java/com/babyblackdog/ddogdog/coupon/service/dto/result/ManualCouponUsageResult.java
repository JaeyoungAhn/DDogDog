package com.babyblackdog.ddogdog.coupon.service.dto.result;

import com.babyblackdog.ddogdog.coupon.domain.vo.CouponUsageStatus;

public record ManualCouponUsageResult(CouponUsageStatus status) {

    public static ManualCouponUsageResult of(CouponUsageStatus changedCouponStatus) {
        return new ManualCouponUsageResult(
                changedCouponStatus
        );
    }
}
