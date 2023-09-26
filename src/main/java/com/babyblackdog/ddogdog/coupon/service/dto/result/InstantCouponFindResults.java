package com.babyblackdog.ddogdog.coupon.service.dto.result;

import com.babyblackdog.ddogdog.coupon.domain.Coupon;
import java.util.List;

public record InstantCouponFindResults(List<InstantCouponFindResult> instantCouponResultPage) {

    public static InstantCouponFindResults of(List<Coupon> retrievedInstantCoupons) {
        List<InstantCouponFindResult> instantCouponFindResultList = retrievedInstantCoupons.stream()
                .map(InstantCouponFindResult::of).toList();

        return new InstantCouponFindResults(instantCouponFindResultList);
    }
}
