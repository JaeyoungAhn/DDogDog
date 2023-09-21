package com.babyblackdog.ddogdog.coupon.service.dto;

import org.springframework.data.domain.Page;

public record ManualCouponFindResults(Page<ManualCouponFindResult> manualCouponResultPage) {

}
