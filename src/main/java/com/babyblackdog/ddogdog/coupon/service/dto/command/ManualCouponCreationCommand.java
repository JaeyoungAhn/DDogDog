package com.babyblackdog.ddogdog.coupon.service.dto.command;

import com.babyblackdog.ddogdog.coupon.controller.dto.request.ManualCouponCreationRequest;
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

    public static ManualCouponCreationCommand of(ManualCouponCreationRequest manualCouponCreationRequest) {
        return new ManualCouponCreationCommand(
                manualCouponCreationRequest.couponName(),
                manualCouponCreationRequest.discountType(),
                manualCouponCreationRequest.discountValue(),
                manualCouponCreationRequest.promoCode(),
                manualCouponCreationRequest.issueCount(),
                manualCouponCreationRequest.startDate(),
                manualCouponCreationRequest.endDate()
        );
    }
}
