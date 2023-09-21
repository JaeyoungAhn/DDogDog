package com.babyblackdog.ddogdog.coupon.controller.dto.response;

import com.babyblackdog.ddogdog.coupon.service.dto.ManualCouponFindResults;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public record ManualCouponFindResponses(Page<ManualCouponFindResponse> manualCouponResponses) {

    public static ManualCouponFindResponses of(ManualCouponFindResults manualCouponFindResults) {
        List<ManualCouponFindResponse> manualCouponFindResponseList = manualCouponFindResults.manualCouponResultPage()
                .getContent()
                .stream().map(ManualCouponFindResponse::of).collect(Collectors.toList());

        Page<ManualCouponFindResponse> responsePage = new PageImpl<>(manualCouponFindResponseList,
                manualCouponFindResults.manualCouponResultPage().getPageable(),
                manualCouponFindResults.manualCouponResultPage().getTotalElements());

        return new ManualCouponFindResponses(responsePage);
    }
}
