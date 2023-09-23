package com.babyblackdog.ddogdog.coupon.service.dto.command;

import java.time.LocalDate;

public record ManualCouponCreationCommand(
        String couponName,
        String discountType,
        Double discountValue,
        String promoCode,
        Long issueCount,
        LocalDate startDate,
        LocalDate endDate
) {

}
