package com.babyblackdog.ddogdog.coupon.application;

import com.babyblackdog.ddogdog.coupon.service.ManualCouponUsageResult;
import com.babyblackdog.ddogdog.coupon.service.dto.InstantCouponCreationResult;
import com.babyblackdog.ddogdog.coupon.service.dto.InstantCouponDeletionResult;
import com.babyblackdog.ddogdog.coupon.service.dto.InstantCouponResults;
import com.babyblackdog.ddogdog.coupon.service.dto.InstantCouponUsageResult;
import com.babyblackdog.ddogdog.coupon.service.dto.ManualCouponCreationResult;
import com.babyblackdog.ddogdog.coupon.service.dto.ManualCouponResults;

public class CouponFacade {

    public ManualCouponCreationResult registerManualCoupon() {
    }

    public InstantCouponCreationResult registerInstantCoupon() {
    }

    public ManualCouponResults findAvailableManualCouponsByEmail(String value) {
    }

    public InstantCouponResults findAvailableInstantCouponsByHotelId(Long hotelId) {
    }

    public ManualCouponUsageResult useManualCoupon(Long couponUsageId) {
    }

    public InstantCouponUsageResult useInstantCoupon() {
    }

    public InstantCouponDeletionResult deleteInstantCoupon(Long couponId) {
    }
}
