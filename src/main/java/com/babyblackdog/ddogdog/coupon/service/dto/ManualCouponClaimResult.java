package com.babyblackdog.ddogdog.coupon.service.dto;

import com.babyblackdog.ddogdog.coupon.domain.CouponUsage;
import com.babyblackdog.ddogdog.coupon.domain.vo.CouponName;
import com.babyblackdog.ddogdog.coupon.domain.vo.CouponType;
import com.babyblackdog.ddogdog.coupon.domain.vo.DiscountType;
import java.time.LocalDate;

public record ManualCouponClaimResult(Long couponUsageId, CouponName couponName, CouponType couponType,
                                      DiscountType discountType,
                                      Double discountValue, LocalDate endDate) {

    public static ManualCouponClaimResult of(CouponUsage savedCouponUsage) {
        return new ManualCouponClaimResult(
                savedCouponUsage.getId(),
                savedCouponUsage.getCoupon().getCouponName(),
                savedCouponUsage.getCoupon().getCouponType(),
                savedCouponUsage.getCoupon().getDiscountValue().getType(),
                savedCouponUsage.getCoupon().getDiscountValue().getValue(),
                savedCouponUsage.getCoupon().getEndDate()
        );
    }
}
