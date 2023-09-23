package com.babyblackdog.ddogdog.coupon.service;

import static com.babyblackdog.ddogdog.global.exception.ErrorCode.COUPON_ALREADY_CLAIMED;
import static com.babyblackdog.ddogdog.global.exception.ErrorCode.COUPON_NOT_FOUND;
import static com.babyblackdog.ddogdog.global.exception.ErrorCode.COUPON_PERMISSION_DENIED;
import static com.babyblackdog.ddogdog.global.exception.ErrorCode.INVALID_COUPON_STATUS;
import static com.babyblackdog.ddogdog.global.exception.ErrorCode.INVALID_INSTANT_COUPON_DATE;

import com.babyblackdog.ddogdog.common.auth.Email;
import com.babyblackdog.ddogdog.common.point.Point;
import com.babyblackdog.ddogdog.coupon.domain.Coupon;
import com.babyblackdog.ddogdog.coupon.domain.CouponUsage;
import com.babyblackdog.ddogdog.coupon.domain.vo.CouponName;
import com.babyblackdog.ddogdog.coupon.domain.vo.CouponPeriod;
import com.babyblackdog.ddogdog.coupon.domain.vo.CouponUsageStatus;
import com.babyblackdog.ddogdog.coupon.domain.vo.DiscountValue;
import com.babyblackdog.ddogdog.coupon.service.dto.command.InstantCouponCreationCommand;
import com.babyblackdog.ddogdog.coupon.service.dto.command.ManualCouponCreationCommand;
import com.babyblackdog.ddogdog.coupon.service.dto.result.InstantCouponCreationResult;
import com.babyblackdog.ddogdog.coupon.service.dto.result.InstantCouponFindResults;
import com.babyblackdog.ddogdog.coupon.service.dto.result.InstantCouponUsageResult;
import com.babyblackdog.ddogdog.coupon.service.dto.result.ManualCouponClaimResult;
import com.babyblackdog.ddogdog.coupon.service.dto.result.ManualCouponCreationResult;
import com.babyblackdog.ddogdog.coupon.service.dto.result.ManualCouponFindResults;
import com.babyblackdog.ddogdog.coupon.service.dto.result.ManualCouponUsageResult;
import com.babyblackdog.ddogdog.global.exception.CouponException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
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
    public ManualCouponCreationResult registerManualCoupon(ManualCouponCreationCommand manualCouponCreationCommand) {
        Coupon savingCoupon = new Coupon(
                new CouponName(manualCouponCreationCommand.couponName()),
                DiscountValue.from(
                        manualCouponCreationCommand.discountType(),
                        manualCouponCreationCommand.discountValue()
                ),
                manualCouponCreationCommand.promoCode(),
                manualCouponCreationCommand.issueCount(),
                new CouponPeriod(
                        manualCouponCreationCommand.startDate(),
                        manualCouponCreationCommand.endDate()
                )
        );

        Coupon savedCoupon = store.registerCoupon(savingCoupon);

        return ManualCouponCreationResult.of(savedCoupon);
    }

    @Override
    @Transactional
    public InstantCouponCreationResult registerInstantCoupon(
            InstantCouponCreationCommand instantCouponCreationCommand) {
        Coupon savingCoupon = new Coupon(
                instantCouponCreationCommand.roomId(),
                new CouponName(instantCouponCreationCommand.couponName()),
                DiscountValue.from(
                        instantCouponCreationCommand.discountType(),
                        instantCouponCreationCommand.discountValue()
                ),
                new CouponPeriod(
                        instantCouponCreationCommand.startDate(),
                        instantCouponCreationCommand.endDate()
                )
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
    @Transactional
    public ManualCouponUsageResult useManualCoupon(Email email, Long referenceId) {
        CouponUsage retrievedCouponUsage = reader.findCouponUsageById(referenceId);

        if (doesNotMatch(email, retrievedCouponUsage.getEmail())) {
            throw new CouponException(COUPON_PERMISSION_DENIED);
        }

        if (statusNotValid(retrievedCouponUsage)) {
            throw new CouponException(INVALID_COUPON_STATUS);
        }

        CouponUsageStatus changedCouponStatus = CouponUsageStatus.USED;

        retrievedCouponUsage.setCouponUsageStatus(changedCouponStatus);
        retrievedCouponUsage.setActivationDate(LocalDate.now());

        return ManualCouponUsageResult.of(changedCouponStatus);
    }

    private static boolean statusNotValid(CouponUsage retrievedCouponUsage) {
        return retrievedCouponUsage.getCouponUsageStatus() != CouponUsageStatus.CLAIMED;
    }

    private static boolean doesNotMatch(Email email, Email retrievedEmail) {
        return !retrievedEmail.getValue().equals(email.getValue());
    }

    @Override
    @Transactional
    public InstantCouponUsageResult useInstantCoupon(Email email, Long referenceId) {
        Coupon retrievedCoupon = reader.findCouponById(referenceId);

        if (isDateNotBetween(retrievedCoupon.getStartDate(), retrievedCoupon.getEndDate())) {
            throw new CouponException(INVALID_INSTANT_COUPON_DATE);
        }

        if (isCancelled(retrievedCoupon)) {
            throw new CouponException(COUPON_NOT_FOUND);
        }

        CouponUsage savingCouponUsage = new CouponUsage(email, retrievedCoupon);

        savingCouponUsage.setActivationDate(LocalDate.now());
        savingCouponUsage.setCouponUsageStatus(CouponUsageStatus.USED);

        CouponUsage savedCouponUsage = store.registerCouponUsage(savingCouponUsage);

        return InstantCouponUsageResult.of(savedCouponUsage);
    }

    private boolean isCancelled(Coupon retrievedCoupon) {
        Long deletionMarker = -1L;
        return (Objects.equals(deletionMarker, retrievedCoupon.getRemainingCount()));
    }

    private static boolean isDateNotBetween(LocalDate startDate, LocalDate endDate) {
        LocalDate timeNow = LocalDate.now();
        return (timeNow.isBefore(startDate) || timeNow.isAfter(endDate));
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
