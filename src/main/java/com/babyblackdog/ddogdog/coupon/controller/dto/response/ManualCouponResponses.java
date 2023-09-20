package com.babyblackdog.ddogdog.coupon.controller.dto.response;

import com.babyblackdog.ddogdog.coupon.service.dto.ManualCouponResults;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public record ManualCouponResponses(Page<ManualCouponResponse> manualCouponResponses) {

    public static ManualCouponResponses of(ManualCouponResults manualCouponResults) {
        List<ManualCouponResponse> manualCouponResponseList = manualCouponResults.manualCouponResultPage().getContent()
                .stream().map(ManualCouponResponse::of).collect(Collectors.toList());

        Page<ManualCouponResponse> responsePage = new PageImpl<>(manualCouponResponseList,
                manualCouponResults.manualCouponResultPage().getPageable(),
                manualCouponResults.manualCouponResultPage().getTotalElements());

        return new ManualCouponResponses(responsePage);
    }
}
