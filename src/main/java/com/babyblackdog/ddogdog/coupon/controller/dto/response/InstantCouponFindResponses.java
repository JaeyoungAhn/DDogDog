package com.babyblackdog.ddogdog.coupon.controller.dto.response;

import com.babyblackdog.ddogdog.coupon.service.dto.InstantCouponFindResults;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public record InstantCouponFindResponses(Page<InstantCouponFindResponse> instantCouponResponses) {

    public static InstantCouponFindResponses of(InstantCouponFindResults instantCouponFindResults) {
        List<InstantCouponFindResponse> instantCouponFindResponseList = instantCouponFindResults.instantCouponResultPage()
                .getContent()
                .stream().map(InstantCouponFindResponse::of).collect(Collectors.toList());

        Page<InstantCouponFindResponse> responsePage = new PageImpl<>(instantCouponFindResponseList,
                instantCouponFindResults.instantCouponResultPage().getPageable(),
                instantCouponFindResults.instantCouponResultPage().getTotalElements());

        return new InstantCouponFindResponses(responsePage);
    }
}
