package com.babyblackdog.ddogdog.coupon.service;

import com.babyblackdog.ddogdog.common.auth.Email;
import com.babyblackdog.ddogdog.common.point.Point;
import com.babyblackdog.ddogdog.coupon.domain.Coupon;
import com.babyblackdog.ddogdog.coupon.domain.CouponUsage;
import com.babyblackdog.ddogdog.coupon.service.dto.InstantCouponCreationResult;
import com.babyblackdog.ddogdog.coupon.service.dto.InstantCouponFindResults;
import com.babyblackdog.ddogdog.coupon.service.dto.InstantCouponUsageResult;
import com.babyblackdog.ddogdog.coupon.service.dto.ManualCouponClaimResult;
import com.babyblackdog.ddogdog.coupon.service.dto.ManualCouponCreationResult;
import com.babyblackdog.ddogdog.coupon.service.dto.ManualCouponFindResults;
import java.time.LocalDate;
import java.util.List;

public interface CouponService {

    /**
     * 수동 할인 쿠폰을 발급
     *
     * @param couponName, discountType, discountValue, promoCode, issueCount, startDate, endDate
     * @return ManualCouponCreationResult
     */
    ManualCouponCreationResult registerManualCoupon(String couponName, String discountType,
            Double discountValue,
            String promoCode, Long issueCount, LocalDate startDate,
            LocalDate endDate);

    /**
     * 즉시 할인 쿠폰을 발급
     *
     * @param roomId, couponName, discountType, discountValue, startDate, endDate
     * @return InstantCouponCreationResult
     */
    InstantCouponCreationResult registerInstantCoupon(Long roomId, String couponName,
            String discountType, Double discountValue,
            LocalDate startDate, LocalDate endDate);

    /**
     * email 의 유저가 가지고 있는 수동 할인 쿠폰을 전체 조회
     *
     * @param email
     * @return ManualCouponResults
     */
    ManualCouponFindResults findAvailableManualCouponsByEmail(Email email);

    /**
     * roomIds 를 받아 적용 가능한 즉시 할인 쿠폰을 다건 조회
     *
     * @param roomIds
     * @return InstantCouponResults
     */
    InstantCouponFindResults findAvailableInstantCouponsByRoomIds(List<Long> roomIds);

    /**
     * couponUsageId 를 이용해 CouponUsage를 조회
     *
     * @param couponUsageId
     * @return CouponUsage
     */
    CouponUsage findCouponUsageById(Long couponUsageId);

    /**
     * promoCode 를 이용해 Coupon 을 조회
     *
     * @param promoCode
     * @return Coupon
     */
    Coupon findCouponByPromoCode(String promoCode);

    /**
     * email, coupon 를 이용해 수동 할인권 수령
     *
     * @param email, coupon
     * @return ManualCouponClaimResult
     */
    ManualCouponClaimResult registerManualCouponUsage(Email email, Coupon coupon);

    /**
     * couponId 를 통해 Coupon 을 조회
     *
     * @param couponId
     * @return Coupon
     */
    Coupon findCouponById(Long couponId);

    /**
     * email, coupon 을 받아 즉시 할인 쿠폰 적용
     *
     * @param email, coupon
     * @return InstantCouponUsageResult
     */
    InstantCouponUsageResult useInstantCoupon(Email email, Coupon coupon);

    /**
     * couponId 를 통해 RoomId 를 조회
     *
     * @param couponId
     * @return roomId
     */
    Long findRoomIdByCouponId(Long couponId);

    /**
     * couponId에 해당하는 즉시 할인 쿠폰 소프트 딜리트
     *
     * @param couponId
     * @return void
     */
    void deleteInstantCoupon(Long couponId);

    /**
     * referenceId(couponUsageId) 에 해당하는 수동 할인 쿠폰 감면액을 반환
     *
     * @param originalPoint
     * @param referenceId
     * @return Point
     */
    Point calculateDiscountAmountForManualCoupon(Point originalPoint, Long referenceId);

    /**
     * referenceId(couponId) 에 해당하는 즉시 할인 쿠폰 감면액을 반환
     *
     * @param originalPoint
     * @param referenceId
     * @return Point
     */
    Point calculateDiscountAmountForInstantCoupon(Point originalPoint, Long referenceId);
}
