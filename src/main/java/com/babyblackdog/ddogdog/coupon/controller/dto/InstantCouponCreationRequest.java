package com.babyblackdog.ddogdog.coupon.controller.dto;

import java.time.LocalDate;

public record InstantCouponCreationRequest(Long roomId, String couponName, String discountType, Double discountValue,
                                           LocalDate startDate, LocalDate endDate) {

}
