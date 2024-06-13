package com.babyblackdog.ddogdog.coupon.repository;

import com.babyblackdog.ddogdog.coupon.domain.Coupon;
import com.babyblackdog.ddogdog.coupon.domain.CouponUsage;
import com.babyblackdog.ddogdog.coupon.service.CouponStore;
import org.springframework.stereotype.Component;

@Component
public class CouponStoreImpl implements CouponStore {

    private final CouponRepository couponRepository;
    private final CouponUsageRepository couponUsageRepository;

    public CouponStoreImpl(CouponRepository couponRepository, CouponUsageRepository couponUsageRepository) {
        this.couponRepository = couponRepository;
        this.couponUsageRepository = couponUsageRepository;
    }

    @Override
    public Coupon registerCoupon(Coupon savingCoupon) {
        return couponRepository.save(savingCoupon);
    }

    @Override
    public CouponUsage registerCouponUsage(CouponUsage couponUsage) {
        return couponUsageRepository.save(couponUsage);
    }

    @Override
    public int decrementCouponCount(String promoCode) {
        return couponRepository.decrementCouponCount(promoCode);
    }
}
