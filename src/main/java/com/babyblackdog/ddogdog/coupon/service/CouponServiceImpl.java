package com.babyblackdog.ddogdog.coupon.service;

import com.babyblackdog.ddogdog.common.auth.Email;
import com.babyblackdog.ddogdog.coupon.domain.Coupon;
import com.babyblackdog.ddogdog.coupon.domain.vo.CouponType;
import com.babyblackdog.ddogdog.coupon.domain.CouponUsage;
import com.babyblackdog.ddogdog.coupon.service.dto.InstantCouponCreationResult;
import com.babyblackdog.ddogdog.coupon.service.dto.InstantCouponFindResults;
import com.babyblackdog.ddogdog.coupon.service.dto.InstantCouponUsageResult;
import com.babyblackdog.ddogdog.coupon.service.dto.ManualCouponClaimResult;
import com.babyblackdog.ddogdog.coupon.service.dto.ManualCouponCreationResult;
import com.babyblackdog.ddogdog.coupon.service.dto.ManualCouponFindResults;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CouponServiceImpl implements CouponService {

    private final CouponStore store;
    private final CouponReader reader;

    @Override
    @Transactional
    public ManualCouponCreationResult registerManualCoupon(String couponName, String discountType, Double discountValue,
            String promoCode, Long issueCount, LocalDate startDate, LocalDate endDate) {
        Coupon savingCoupon = new Coupon(couponName, discountType, discountValue, promoCode, issueCount, startDate, endDate);

        savingCoupon.setCouponType(CouponType.MANUAL);
        savingCoupon.setRemainingCount(issueCount);
        Coupon savedCoupon = store.registerManualCoupon(savingCoupon);

        return ManualCouponCreationResult.of(savedCoupon);
    }

    @Override
    @Transactional
    public InstantCouponCreationResult registerInstantCoupon(Long roomId, String couponName, String discountType,
            Double discountValue, LocalDate startDate, LocalDate endDate) {
        return null;
    }

    @Override
    public ManualCouponFindResults findAvailableManualCouponsByEmail(Email email) {
        return null;
    }

    @Override
    public InstantCouponFindResults findAvailableInstantCouponsByRoomIds(List<Long> roomIds) {
        return null;
    }

    @Override
    @Transactional
    public ManualCouponClaimResult claimManualCoupon(Email email, String promoCode) {
        return null;
    }

    @Override
    public CouponUsage findCouponUsageById(Long couponUsageId) {
        return null;
    }

    @Override
    public Coupon findCouponByPromoCode(String promoCode) {
        return null;
    }

    @Override
    @Transactional
    public ManualCouponClaimResult registerManualCouponUsage(Email email, Long couponId) {
        return null;
    }

    @Override
    public Coupon findCouponById(Long couponId) {
        return null;
    }

    @Override
    @Transactional
    public InstantCouponUsageResult useInstantCoupon(Email email, Long couponId) {
        return null;
    }

    @Override
    public Long findRoomIdByCouponId(Long couponId) {
        return null;
    }

    @Override
    @Transactional
    public void deleteInstantCoupon(Long couponId) {

    }
}
