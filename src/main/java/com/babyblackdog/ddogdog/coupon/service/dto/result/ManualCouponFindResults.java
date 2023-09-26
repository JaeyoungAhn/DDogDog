package com.babyblackdog.ddogdog.coupon.service.dto.result;

import com.babyblackdog.ddogdog.coupon.domain.CouponUsage;
import java.util.List;

public record ManualCouponFindResults(List<ManualCouponFindResult> manualCouponResultPage) {

    public static ManualCouponFindResults of(List<CouponUsage> retrievedManualCoupons) {
        List<ManualCouponFindResult> manualCouponFindResultList = retrievedManualCoupons.stream()
                .map(ManualCouponFindResult::of).toList();

        return new ManualCouponFindResults(manualCouponFindResultList);
    }
}
