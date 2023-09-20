package com.babyblackdog.ddogdog.coupon.service.dto;

import java.time.LocalDate;

public record ManualCouponClaimResult(Long couponUsageId, String couponName, String couponType, String discountType,
                                      Double discountValue, LocalDate endDate) {

}
