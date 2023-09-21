package com.babyblackdog.ddogdog.coupon.service;

import com.babyblackdog.ddogdog.coupon.domain.Coupon;
import com.babyblackdog.ddogdog.coupon.domain.CouponUsage;

public interface CouponStore {

    Coupon registerCoupon(Coupon savingCoupon);

    CouponUsage registerCouponUsage(CouponUsage couponUsage);

    void deleteInstantCoupon(Long couponId);

}