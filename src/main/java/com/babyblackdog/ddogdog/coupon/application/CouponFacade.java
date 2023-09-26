package com.babyblackdog.ddogdog.coupon.application;

import static com.babyblackdog.ddogdog.global.exception.ErrorCode.COUPON_OUT_OF_STOCK;
import static com.babyblackdog.ddogdog.global.exception.ErrorCode.COUPON_PERMISSION_DENIED;
import static com.babyblackdog.ddogdog.global.exception.ErrorCode.INVALID_DISCOUNT_TYPE;

import com.babyblackdog.ddogdog.common.auth.Email;
import com.babyblackdog.ddogdog.common.auth.JwtSimpleAuthentication;
import com.babyblackdog.ddogdog.common.point.Point;
import com.babyblackdog.ddogdog.coupon.domain.Coupon;
import com.babyblackdog.ddogdog.coupon.service.CouponService;
import com.babyblackdog.ddogdog.coupon.service.dto.command.InstantCouponCreationCommand;
import com.babyblackdog.ddogdog.coupon.service.dto.command.ManualCouponCreationCommand;
import com.babyblackdog.ddogdog.coupon.service.dto.result.InstantCouponCreationResult;
import com.babyblackdog.ddogdog.coupon.service.dto.result.InstantCouponFindResults;
import com.babyblackdog.ddogdog.coupon.service.dto.result.ManualCouponClaimResult;
import com.babyblackdog.ddogdog.coupon.service.dto.result.ManualCouponCreationResult;
import com.babyblackdog.ddogdog.coupon.service.dto.result.ManualCouponFindResults;
import com.babyblackdog.ddogdog.coupon.service.dto.result.ManualCouponGiftResult;
import com.babyblackdog.ddogdog.global.exception.CouponException;
import com.babyblackdog.ddogdog.notification.NotificationService;
import com.babyblackdog.ddogdog.place.accessor.PlaceAccessService;
import com.babyblackdog.ddogdog.place.service.dto.HotelResult;
import com.babyblackdog.ddogdog.user.accessor.UserAccessorService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CouponFacade {

    private final CouponService service;
    private final PlaceAccessService placeAccessService;
    private final UserAccessorService userAccessorService;
    private final JwtSimpleAuthentication authentication;
    private final NotificationService notificationService;

    public CouponFacade(CouponService service, PlaceAccessService placeAccessService,
            UserAccessorService userAccessorService, JwtSimpleAuthentication authentication,
            NotificationService notificationService) {
        this.service = service;
        this.placeAccessService = placeAccessService;
        this.userAccessorService = userAccessorService;
        this.authentication = authentication;
        this.notificationService = notificationService;
    }

    public ManualCouponCreationResult registerManualCoupon(ManualCouponCreationCommand manualCouponCreationCommand) {
        if (!userAccessorService.isAdmin()) {
            throw new CouponException(COUPON_PERMISSION_DENIED);
        }

        ManualCouponCreationResult manualCouponCreationResult = service.registerManualCoupon(
                manualCouponCreationCommand);

        ManualCouponGiftResult manualCouponGiftResult = ManualCouponGiftResult.of(manualCouponCreationResult);

        notificationService.notify(manualCouponGiftResult);

        return manualCouponCreationResult;
    }

    public InstantCouponCreationResult registerInstantCoupon(
            InstantCouponCreationCommand instantCouponCreationCommand) {

        if (isNotRoomOwner(authentication.getEmail(), instantCouponCreationCommand.roomId())) {
            throw new CouponException(COUPON_PERMISSION_DENIED);
        }

        return service.registerInstantCoupon(instantCouponCreationCommand);
    }

    public void deleteInstantCoupon(Long couponId) {
        Long roomId = service.findRoomIdByCouponId(couponId);

        if (isNotRoomOwner(authentication.getEmail(), roomId)) {
            throw new CouponException(COUPON_PERMISSION_DENIED);
        }

        service.deleteInstantCoupon(couponId);
    }

    private boolean isNotRoomOwner(Email email, Long roomId) {
        HotelResult retrievedHotelResult = placeAccessService.findHotelByEmail(email);
        List<Long> roomIds = placeAccessService.findRoomIdsOfHotel(retrievedHotelResult.id());

        return !roomIds.contains(roomId);
    }

    public ManualCouponFindResults findAvailableManualCouponsByEmail(Email email) {
        return service.findAvailableManualCouponsByEmail(email);
    }

    public InstantCouponFindResults findAvailableInstantCouponsByHotelId(Long hotelId) {
        List<Long> roomIds = placeAccessService.findRoomIdsOfHotel(hotelId);

        return service.findAvailableInstantCouponsByRoomIds(roomIds);
    }

    @Transactional
    public ManualCouponClaimResult claimManualCoupon(Email email, String promoCode) {
        Coupon retrievedCoupon = service.findCouponByPromoCode(promoCode);

        Long remainingCount = retrievedCoupon.getRemainingCount();

        if (remainingCount <= 0) {
            throw new CouponException(COUPON_OUT_OF_STOCK);
        }

        retrievedCoupon.setRemainingCount(remainingCount - 1);

        return service.registerManualCouponUsage(email, retrievedCoupon);
    }

    @Transactional
    public Point calculateDiscountAmount(Point originalPoint, Long referenceId, String couponType) {
        Email email = authentication.getEmail();

        switch (couponType.toLowerCase()) {
            case "manual" -> {
                service.useManualCoupon(email, referenceId);
                return service.calculateDiscountAmountForManualCoupon(originalPoint, referenceId);
            }
            case "instant" -> {
                service.useInstantCoupon(email, referenceId);
                return service.calculateDiscountAmountForInstantCoupon(originalPoint, referenceId);
            }
            default -> throw new CouponException(INVALID_DISCOUNT_TYPE);
        }
    }

}