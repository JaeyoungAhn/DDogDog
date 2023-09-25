package com.babyblackdog.ddogdog.coupon.service.dto.result;

import com.babyblackdog.ddogdog.coupon.domain.Coupon;
import com.babyblackdog.ddogdog.coupon.domain.vo.CouponType;
import com.babyblackdog.ddogdog.coupon.domain.vo.DiscountType;
import java.time.LocalDate;

public record InstantCouponFindResult(Long couponId, Long roomId, String couponName, CouponType couponType,
                                      DiscountType discountType, Double discountValue, LocalDate endDate) {

    public static InstantCouponFindResult of(Coupon coupon) {
        return new InstantCouponFindResult(
                coupon.getId(),
                coupon.getRoomId(),
                coupon.getCouponName().getValue(),
                coupon.getCouponType(),
                coupon.getDiscountValue().getType(),
                coupon.getDiscountValue().getValue(),
                coupon.getEndDate());
    }
}
