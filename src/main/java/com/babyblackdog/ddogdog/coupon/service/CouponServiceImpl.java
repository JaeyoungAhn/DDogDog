package com.babyblackdog.ddogdog.coupon.service;

import static com.babyblackdog.ddogdog.global.exception.ErrorCode.COUPON_ALREADY_CLAIMED;

import com.babyblackdog.ddogdog.common.auth.Email;
import com.babyblackdog.ddogdog.common.point.Point;
import com.babyblackdog.ddogdog.coupon.domain.Coupon;
import com.babyblackdog.ddogdog.coupon.domain.CouponUsage;
import com.babyblackdog.ddogdog.coupon.domain.vo.CouponName;
import com.babyblackdog.ddogdog.coupon.domain.vo.CouponPeriod;
import com.babyblackdog.ddogdog.coupon.domain.vo.CouponType;
import com.babyblackdog.ddogdog.coupon.domain.vo.DiscountValue;
import com.babyblackdog.ddogdog.coupon.service.dto.InstantCouponCreationResult;
import com.babyblackdog.ddogdog.coupon.service.dto.InstantCouponFindResults;
import com.babyblackdog.ddogdog.coupon.service.dto.InstantCouponUsageResult;
import com.babyblackdog.ddogdog.coupon.service.dto.ManualCouponClaimResult;
import com.babyblackdog.ddogdog.coupon.service.dto.ManualCouponCreationResult;
import com.babyblackdog.ddogdog.coupon.service.dto.ManualCouponFindResults;
import com.babyblackdog.ddogdog.global.exception.CouponException;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CouponServiceImpl implements CouponService {

    private final CouponStore store;
    private final CouponReader reader;

    public CouponServiceImpl(CouponStore store, CouponReader reader) {
        this.store = store;
        this.reader = reader;
    }

    @Override
    @Transactional
    public ManualCouponCreationResult registerManualCoupon(String couponName, String discountType, Double discountValue,
            String promoCode, Long issueCount, LocalDate startDate, LocalDate endDate) {
        Coupon savingCoupon = new Coupon(
                new CouponName(couponName),
                CouponType.MANUAL,
                DiscountValue.from(discountType, discountValue),
                promoCode,
                issueCount,
                new CouponPeriod(startDate, endDate)
        );

        savingCoupon.setRemainingCount(issueCount);
        Coupon savedCoupon = store.registerCoupon(savingCoupon);

        return ManualCouponCreationResult.of(savedCoupon);
    }

    @Override
    @Transactional
    public InstantCouponCreationResult registerInstantCoupon(Long roomId, String couponName, String discountType,
            Double discountValue, LocalDate startDate, LocalDate endDate) {
        Coupon savingCoupon = new Coupon(
                roomId,
                CouponType.INSTANT,
                new CouponName(couponName),
                DiscountValue.from(discountType, discountValue),
                new CouponPeriod(startDate, endDate)
        );

        Coupon savedCoupon = store.registerCoupon(savingCoupon);

        return InstantCouponCreationResult.of(savedCoupon);
    }

    @Override
    public ManualCouponFindResults findAvailableManualCouponsByEmail(Email email) {
        List<CouponUsage> retrievedManualCoupons = reader.findManualCouponsByEmail(email);

        return ManualCouponFindResults.of(retrievedManualCoupons);
    }

    @Override
    public InstantCouponFindResults findAvailableInstantCouponsByRoomIds(List<Long> roomIds) {
        List<Coupon> retrievedInstantCoupons = reader.findAvailableInstantCouponsByRoomIds(roomIds);

        return InstantCouponFindResults.of(retrievedInstantCoupons);
    }

    @Override
    public CouponUsage findCouponUsageById(Long couponUsageId) {
        return reader.findCouponUsageById(couponUsageId);
    }

    @Override
    public Coupon findCouponByPromoCode(String promoCode) {
        return reader.findCouponByPromoCode(promoCode);
    }

    @Override
    @Transactional
    public ManualCouponClaimResult registerManualCouponUsage(Email email, Coupon coupon) {
        if (reader.doesCouponUsageExistByEmailAndCouponId(email, coupon.getId())) {
            throw new CouponException(COUPON_ALREADY_CLAIMED);
        }

        CouponUsage savingCouponUsage = new CouponUsage(email, coupon);
        CouponUsage savedCouponUsage = store.registerCouponUsage(savingCouponUsage);

        return ManualCouponClaimResult.of(savedCouponUsage);
    }

    @Override
    public Coupon findCouponById(Long couponId) {
        return reader.findCouponById(couponId);
    }

    @Override
    @Transactional
    public InstantCouponUsageResult useInstantCoupon(Email email, Coupon coupon) {
        CouponUsage savingCouponUsage = new CouponUsage(email, coupon);
        savingCouponUsage.setActivationDate(LocalDate.now());
        CouponUsage savedCouponUsage = store.registerCouponUsage(savingCouponUsage);

        return InstantCouponUsageResult.of(savedCouponUsage);
    }

    @Override
    public Long findRoomIdByCouponId(Long couponId) {
        return reader.findRoomIdByCouponId(couponId);
    }

    @Override
    @Transactional
    public void deleteInstantCoupon(Long couponId) {
        Coupon retrievedCoupon = reader.findCouponById(couponId);

        Long deletionMarker = -1L;

        retrievedCoupon.setRemainingCount(deletionMarker);
    }

    @Override
    public Point calculateDiscountAmountForManualCoupon(Point originalPoint, Long couponUsageId) {
        Coupon retrievedCoupon = reader.findCouponByCouponUsageId(couponUsageId);
        return retrievedCoupon.getDiscountValue().getDiscount(originalPoint);
    }

    @Override
    public Point calculateDiscountAmountForInstantCoupon(Point originalPoint, Long couponId) {
        Coupon retrievedCoupon = reader.findCouponById(couponId);
        return retrievedCoupon.getDiscountValue().getDiscount(originalPoint);
    }
}
