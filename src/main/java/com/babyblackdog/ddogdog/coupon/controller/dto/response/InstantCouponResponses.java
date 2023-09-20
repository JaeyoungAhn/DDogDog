package com.babyblackdog.ddogdog.coupon.controller.dto.response;

import com.babyblackdog.ddogdog.coupon.service.dto.InstantCouponResults;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public record InstantCouponResponses(Page<InstantCouponResponse> instantCouponResponses) {

    public static InstantCouponResponses of(InstantCouponResults instantCouponResults) {
        List<InstantCouponResponse> instantCouponResponseList = instantCouponResults.instantCouponResultPage()
                .getContent()
                .stream().map(InstantCouponResponse::of).collect(Collectors.toList());

        Page<InstantCouponResponse> responsePage = new PageImpl<>(instantCouponResponseList,
                instantCouponResults.instantCouponResultPage().getPageable(),
                instantCouponResults.instantCouponResultPage().getTotalElements());

        return new InstantCouponResponses(responsePage);
    }
}
