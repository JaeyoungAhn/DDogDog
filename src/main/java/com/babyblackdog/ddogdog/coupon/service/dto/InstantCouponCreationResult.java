package com.babyblackdog.ddogdog.coupon.service.dto;

import com.babyblackdog.ddogdog.coupon.domain.Coupon;
import com.babyblackdog.ddogdog.coupon.domain.vo.CouponName;
import com.babyblackdog.ddogdog.coupon.domain.vo.CouponType;
import com.babyblackdog.ddogdog.coupon.domain.vo.DiscountType;
import java.time.LocalDate;

public record InstantCouponCreationResult(Long couponId, Long roomId, CouponName couponName, CouponType couponType,
                                          DiscountType discountType, Double discountValue, LocalDate startDate,
                                          LocalDate endDate) {

    public static InstantCouponCreationResult of(Coupon savedCoupon) {
        return new InstantCouponCreationResult(
                savedCoupon.getId(),
                savedCoupon.getRoomId(),
                savedCoupon.getCouponName(),
                savedCoupon.getCouponType(),
                savedCoupon.getDiscountValue().getType(),
                savedCoupon.getDiscountValue().getValue(),
                savedCoupon.getStartDate(),
                savedCoupon.getEndDate()
        );
    }
}
