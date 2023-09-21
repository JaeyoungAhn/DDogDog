package com.babyblackdog.ddogdog.coupon.service;

import com.babyblackdog.ddogdog.coupon.domain.Coupon;
import com.babyblackdog.ddogdog.coupon.domain.CouponUsage;

public interface CouponStore {

    Coupon registerManualCoupon(Coupon coupon);

    Coupon registerInstantCoupon(Coupon coupon);

    CouponUsage claimManualCoupon(CouponUsage couponUsage);

    CouponUsage useInstantCoupon(CouponUsage couponUsage);

    void deleteInstantCoupon(Long couponId);
}