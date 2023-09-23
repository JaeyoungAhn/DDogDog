package com.babyblackdog.ddogdog.coupon.service.dto.command;

import com.babyblackdog.ddogdog.common.auth.Email;
import java.time.LocalDate;

public record InstantCouponCreationCommand(
        Email email,
        Long roomId,
        String couponName,
        String discountType,
        Double discountValue,
        LocalDate startDate,
        LocalDate endDate
) {

}
