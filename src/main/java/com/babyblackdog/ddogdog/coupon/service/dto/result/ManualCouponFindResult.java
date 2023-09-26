package com.babyblackdog.ddogdog.coupon.service.dto.result;

import com.babyblackdog.ddogdog.coupon.domain.CouponUsage;
import com.babyblackdog.ddogdog.coupon.domain.vo.CouponName;
import com.babyblackdog.ddogdog.coupon.domain.vo.CouponType;
import com.babyblackdog.ddogdog.coupon.domain.vo.DiscountType;
import java.time.LocalDate;

public record ManualCouponFindResult(Long couponUsageId, CouponName couponName, CouponType couponType,
                                     DiscountType discountType,
                                     Double discountValue, LocalDate endDate) {

    public static ManualCouponFindResult of(CouponUsage couponUsage) {
        return new ManualCouponFindResult(
                couponUsage.getId(),
                couponUsage.getCoupon().getCouponName(),
                couponUsage.getCoupon().getCouponType(),
                couponUsage.getCoupon().getDiscountValue().getType(),
                couponUsage.getCoupon().getDiscountValue().getValue(),
                couponUsage.getCoupon().getEndDate()
        );
    }
}
