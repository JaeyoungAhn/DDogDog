package com.babyblackdog.ddogdog.coupon.controller.dto.response;

import com.babyblackdog.ddogdog.coupon.service.dto.result.InstantCouponFindResults;
import java.util.List;

public record InstantCouponFindResponses(List<InstantCouponFindResponse> instantCouponResponses) {

    public static InstantCouponFindResponses of(InstantCouponFindResults instantCouponFindResults) {
        List<InstantCouponFindResponse> instantCouponFindResponseList = instantCouponFindResults.instantCouponResultPage()
                .stream().map(InstantCouponFindResponse::of).toList();

        return new InstantCouponFindResponses(instantCouponFindResponseList);
    }
}
