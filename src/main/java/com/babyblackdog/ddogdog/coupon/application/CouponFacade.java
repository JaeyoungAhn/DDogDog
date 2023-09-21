package com.babyblackdog.ddogdog.coupon.application;

import static com.babyblackdog.ddogdog.global.exception.ErrorCode.INSTANT_COUPON_DATE_NOT_VALID;
import static com.babyblackdog.ddogdog.global.exception.ErrorCode.INVALID_COUPON_STATUS;
import static com.babyblackdog.ddogdog.global.exception.ErrorCode.PERMISSION_DENIED_COUPON;

import com.babyblackdog.ddogdog.common.auth.Email;
import com.babyblackdog.ddogdog.coupon.domain.Coupon;
import com.babyblackdog.ddogdog.coupon.domain.CouponUsage;
import com.babyblackdog.ddogdog.coupon.domain.CouponUsageStatus;
import com.babyblackdog.ddogdog.coupon.service.CouponService;
import com.babyblackdog.ddogdog.coupon.service.dto.InstantCouponCreationResult;
import com.babyblackdog.ddogdog.coupon.service.dto.InstantCouponFindResults;
import com.babyblackdog.ddogdog.coupon.service.dto.InstantCouponUsageResult;
import com.babyblackdog.ddogdog.coupon.service.dto.ManualCouponClaimResult;
import com.babyblackdog.ddogdog.coupon.service.dto.ManualCouponCreationResult;
import com.babyblackdog.ddogdog.coupon.service.dto.ManualCouponFindResults;
import com.babyblackdog.ddogdog.coupon.service.dto.ManualCouponUsageResult;
import com.babyblackdog.ddogdog.global.exception.CouponException;
import com.babyblackdog.ddogdog.place.accessor.PlaceAccessService;
import com.babyblackdog.ddogdog.user.accessor.UserAccessorService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CouponFacade {

    private final CouponService service;
    private final PlaceAccessService placeAccessService;
    private final UserAccessorService userAccessorService;

    public CouponFacade(CouponService service, PlaceAccessService placeAccessService,
            UserAccessorService userAccessorService) {
        this.service = service;
        this.placeAccessService = placeAccessService;
        this.userAccessorService = userAccessorService;
    }

    public ManualCouponCreationResult registerManualCoupon(Email email, String couponName, String discountType,
            Double discountValue,
            String promoCode, Long issueCount, LocalDate startDate,
            LocalDate endDate) {

        if (!userAccessorService.isWoonYoungJa(email)) {
            throw new CouponException(PERMISSION_DENIED_COUPON);
        }

        return service.registerManualCoupon(couponName, discountType, discountValue, promoCode, issueCount, startDate,
                endDate);
    }

    public InstantCouponCreationResult registerInstantCoupon(Email email, Long roomId, String couponName,
            String discountType, Double discountValue,
            LocalDate startDate, LocalDate endDate) {

        if (!placeAccessService.hasRoomAccess(email, roomId)) {
            throw new CouponException(PERMISSION_DENIED_COUPON);
        }

        return service.registerInstantCoupon(roomId, couponName, discountType, discountValue, startDate, endDate);
    }

    public ManualCouponFindResults findAvailableManualCouponsByEmail(Email email) {
        return service.findAvailableManualCouponsByEmail(email);
    }

    public InstantCouponFindResults findAvailableInstantCouponsByHotelId(Long hotelId) {
        List<Long> roomIds = placeAccessService.findRoomIdsOfHotel(hotelId);

        return service.findAvailableInstantCouponsByRoomIds(roomIds);
    }

    public ManualCouponClaimResult claimManualCoupon(Email email, String promoCode) {
        Coupon retrievedCoupon = service.findCouponByPromoCode(promoCode);

        // todo : 동시성 문제
        return service.registerManualCouponUsage(email, retrievedCoupon.getId());
    }

    @Transactional
    public ManualCouponUsageResult useManualCoupon(Email email, Long couponUsageId) {

        CouponUsage retrievedCouponUsage = service.findCouponUsageById(couponUsageId);

        if (doesNotMatch(email, retrievedCouponUsage.getEmail())) {
            throw new CouponException(PERMISSION_DENIED_COUPON);
        }

        if (statusNotValid(retrievedCouponUsage)) {
            throw new CouponException(INVALID_COUPON_STATUS);
        }

        CouponUsageStatus changedCouponStatus = CouponUsageStatus.USED;

        retrievedCouponUsage.setCouponUsageStatus(changedCouponStatus);
        retrievedCouponUsage.setActivationDate(LocalDate.now());

        return new ManualCouponUsageResult(changedCouponStatus);

        // todo : 실제 가격 변동 처리..
        // todo : order에 couponUsageId가 notNull인 경우, 쿠폰 측에 할인후 가격을 요청
    }

    private static boolean statusNotValid(CouponUsage retrievedCouponUsage) {
        return retrievedCouponUsage.getCouponUsageStatus() != CouponUsageStatus.CLAIMED;
    }

    private static boolean doesNotMatch(Email email, Email retrievedEmail) {
        return !retrievedEmail.getValue().equals(email.getValue());
    }

    public InstantCouponUsageResult useInstantCoupon(Email email, Long couponId) {
        Coupon retrievedCoupon = service.findCouponById(couponId);

        if (isDateNotBetween(retrievedCoupon.getStartDate(), retrievedCoupon.getEndDate())) {
            throw new CouponException(INSTANT_COUPON_DATE_NOT_VALID);
        }

        return service.useInstantCoupon(email, couponId);

        // todo : 실제 가격 변동 처리
        // todo : order에 couponUsageId가 notNull인 경우, 쿠폰 측에 할인 후 가격을 요청
    }

    private static boolean isDateNotBetween(LocalDate startDate, LocalDate endDate) {
        LocalDate timeNow = LocalDate.now();
        return (timeNow.isBefore(startDate) || timeNow.isAfter(endDate));
    }

    @Transactional
    public void deleteInstantCoupon(Email email, Long couponId) {
        Long roomId = service.findRoomIdByCouponId(couponId);

        if (!placeAccessService.hasRoomAccess(email, roomId)) {
            throw new CouponException(PERMISSION_DENIED_COUPON);
        }

        service.deleteInstantCoupon(couponId);
    }
}