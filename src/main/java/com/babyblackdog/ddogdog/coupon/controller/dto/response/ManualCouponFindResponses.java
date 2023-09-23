package com.babyblackdog.ddogdog.coupon.controller.dto.response;

import com.babyblackdog.ddogdog.coupon.service.dto.ManualCouponFindResults;
import java.util.List;

public record ManualCouponFindResponses(List<ManualCouponFindResponse> manualCouponResponses) {

    public static ManualCouponFindResponses of(ManualCouponFindResults manualCouponFindResults) {
        List<ManualCouponFindResponse> manualCouponFindResponseList = manualCouponFindResults.manualCouponResultPage()
                .stream().map(ManualCouponFindResponse::of).toList();

        return new ManualCouponFindResponses(manualCouponFindResponseList);
    }
}
