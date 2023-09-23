package com.babyblackdog.ddogdog.coupon.service.dto.result;

import com.babyblackdog.ddogdog.coupon.domain.vo.CouponName;
import com.babyblackdog.ddogdog.coupon.domain.vo.CouponType;
import com.babyblackdog.ddogdog.coupon.domain.vo.DiscountType;

public record ManualCouponGiftResult(CouponName couponName, CouponType couponType,
                                     DiscountType discountType,
                                     Double discountValue,
                                     String promoCode) {

    public static ManualCouponGiftResult of(ManualCouponCreationResult result) {
        return new ManualCouponGiftResult(
                result.couponName(),
                result.couponType(),
                result.discountType(),
                result.discountValue(),
                result.promoCode()
        );
    }
}
