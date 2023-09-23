package com.babyblackdog.ddogdog.coupon.service.dto.command;

import com.babyblackdog.ddogdog.coupon.controller.dto.request.InstantCouponCreationRequest;
import java.time.LocalDate;

public record InstantCouponCreationCommand(
        Long roomId,
        String couponName,
        String discountType,
        Double discountValue,
        LocalDate startDate,
        LocalDate endDate
) {

    public static InstantCouponCreationCommand of(InstantCouponCreationRequest instantCouponCreationRequest) {
        return new InstantCouponCreationCommand(
                instantCouponCreationRequest.roomId(),
                instantCouponCreationRequest.couponName(),
                instantCouponCreationRequest.discountType(),
                instantCouponCreationRequest.discountValue(),
                instantCouponCreationRequest.startDate(),
                instantCouponCreationRequest.endDate()
        );
    }
}
