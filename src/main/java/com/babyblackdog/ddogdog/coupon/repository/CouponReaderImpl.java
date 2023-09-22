package com.babyblackdog.ddogdog.coupon.repository;

import static com.babyblackdog.ddogdog.global.exception.ErrorCode.COUPON_NOT_FOUND;
import static com.babyblackdog.ddogdog.global.exception.ErrorCode.COUPON_USAGE_NOT_FOUND;

import com.babyblackdog.ddogdog.common.auth.Email;
import com.babyblackdog.ddogdog.coupon.domain.Coupon;
import com.babyblackdog.ddogdog.coupon.domain.CouponUsage;
import com.babyblackdog.ddogdog.coupon.domain.vo.CouponUsageStatus;
import com.babyblackdog.ddogdog.coupon.service.CouponReader;
import com.babyblackdog.ddogdog.global.exception.CouponException;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CouponReaderImpl implements CouponReader {

    private final CouponRepository couponRepository;
    private final CouponUsageRepository couponUsageRepository;

    public CouponReaderImpl(CouponRepository couponRepository, CouponUsageRepository couponUsageRepository) {
        this.couponRepository = couponRepository;
        this.couponUsageRepository = couponUsageRepository;
    }

    @Override
    public List<CouponUsage> findManualCouponsByEmail(Email email) {
        return couponUsageRepository.findCouponUsagesByEmailAndCouponUsageStatus(email, CouponUsageStatus.CLAIMED);
    }

    @Override
    public List<Coupon> findAvailableInstantCouponsByRoomIds(List<Long> roomIds) {
        return couponRepository.findByRoomIdInAndRemainingCountIsNull(roomIds);
    }

    @Override
    public CouponUsage findCouponUsageById(Long couponUsageId) {
        return couponUsageRepository.findById(couponUsageId).
                orElseThrow(() -> new CouponException(COUPON_USAGE_NOT_FOUND));
    }

    @Override
    public Coupon findCouponByPromoCode(String promoCode) {
        return couponRepository.findCouponByPromoCode(promoCode);
    }

    @Override
    public Coupon findCouponById(Long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(() -> new CouponException(COUPON_NOT_FOUND));
    }

    @Override
    public Long findRoomIdByCouponId(Long couponId) {
        return couponRepository.findRoomIdById(couponId);
    }

    @Override
    public Coupon findCouponByCouponUsageId(Long couponUsageId) {
        CouponUsage retrievedCouponUsage = couponUsageRepository.findWithCouponById(couponUsageId)
                .orElseThrow(() -> new CouponException(COUPON_USAGE_NOT_FOUND));
        return retrievedCouponUsage.getCoupon();
    }

    @Override
    public boolean doesCouponUsageExistByEmailAndCouponId(Email email, Long couponId) {
        return couponUsageRepository.existsByEmailAndCouponId(email, couponId);
    }
}
