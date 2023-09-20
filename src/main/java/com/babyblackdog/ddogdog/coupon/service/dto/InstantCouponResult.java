package com.babyblackdog.ddogdog.coupon.service.dto;

import java.time.LocalDate;

public record InstantCouponResult(Long couponId, Long roomId, String couponName, String couponType,
                                  String discountType, Double discountValue, LocalDate endDate) {

}
