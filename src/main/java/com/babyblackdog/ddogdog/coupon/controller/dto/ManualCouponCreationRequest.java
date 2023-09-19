package com.babyblackdog.ddogdog.coupon.controller.dto;

import java.time.LocalDate;

public record ManualCouponCreationRequest(String couponName, String discountType, Double discountValue,
                                          String redemptionCode,
                                          Long issueCount, LocalDate startDate, LocalDate endDate) {

}
