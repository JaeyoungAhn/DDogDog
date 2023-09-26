package com.babyblackdog.ddogdog.coupon.controller.dto.request;

import java.time.LocalDate;

public record ManualCouponCreationRequest(String couponName, String discountType, Double discountValue,
                                          String promoCode, Long issueCount, LocalDate startDate,
                                          LocalDate endDate) {

}
