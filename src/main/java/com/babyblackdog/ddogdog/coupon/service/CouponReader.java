package com.babyblackdog.ddogdog.coupon.service;

import com.babyblackdog.ddogdog.common.auth.Email;
import com.babyblackdog.ddogdog.coupon.domain.Coupon;
import com.babyblackdog.ddogdog.coupon.domain.CouponUsage;
import java.util.List;

public interface CouponReader {

    List<CouponUsage> findManualCouponsByEmail(Email email);

    List<Coupon> findInstantCouponsByRoomIds(List<Long> roomIds);
}