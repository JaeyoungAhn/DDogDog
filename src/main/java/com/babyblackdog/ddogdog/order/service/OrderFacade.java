package com.babyblackdog.ddogdog.order.service;

import com.babyblackdog.ddogdog.common.point.Point;
import com.babyblackdog.ddogdog.coupon.application.CouponFacade;
import com.babyblackdog.ddogdog.order.service.dto.result.OrderCancelResult;
import com.babyblackdog.ddogdog.order.service.dto.result.OrderCreateResult;
import com.babyblackdog.ddogdog.order.service.dto.result.RoomOrderPageResult;
import com.babyblackdog.ddogdog.place.accessor.PlaceAccessService;
import com.babyblackdog.ddogdog.place.accessor.vo.RoomSimpleResult;
import com.babyblackdog.ddogdog.reservation.service.ReservationService;
import com.babyblackdog.ddogdog.reservation.service.StayPeriod;
import com.babyblackdog.ddogdog.user.accessor.UserAccessorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderFacade {

    private final OrderService service;
    private final PlaceAccessService placeAccessService;
    private final ReservationService reservationService;
    private final UserAccessorService userAccessorService;
    private final StayCostEstimator stayCostEstimator;
    private final CouponFacade couponFacade;

    public OrderFacade(OrderService service, PlaceAccessService placeAccessService,
            ReservationService reservationService, UserAccessorService userAccessorService,
            StayCostEstimator stayCostEstimator, CouponFacade couponFacade) {
        this.service = service;
        this.placeAccessService = placeAccessService;
        this.reservationService = reservationService;
        this.userAccessorService = userAccessorService;
        this.stayCostEstimator = stayCostEstimator;
        this.couponFacade = couponFacade;
    }

    @Transactional(readOnly = true)
    public RoomOrderPageResult findRoomSummary(Long roomId, StayPeriod stayPeriod) {
        RoomSimpleResult roomSimpleResult = placeAccessService.findRoomSimpleInfo(roomId);

        return new RoomOrderPageResult(
                roomSimpleResult.hotelName(),
                roomSimpleResult.roomType(),
                roomSimpleResult.roomDescription(),
                roomSimpleResult.roomNumber(),
                stayCostEstimator.calculateTotalCost(stayPeriod, roomSimpleResult.point()),
                stayPeriod.getCheckIn(),
                stayPeriod.getCheckOut()
        );
    }

    public OrderCreateResult order(Long roomId, StayPeriod stayPeriod, Long couponReferenceId, String couponType) {
        RoomSimpleResult roomInfo = placeAccessService.findRoomSimpleInfo(roomId);

        Point pointToPay = stayCostEstimator.calculateTotalCost(stayPeriod, roomInfo.point());

        if (isCouponApplicable(couponReferenceId, couponType)) {
            Point pointToBeDiscounted = couponFacade.calculateDiscountAmount(pointToPay, couponReferenceId, couponType);
            pointToPay = Point.subPoint(pointToPay, pointToBeDiscounted);
        }

        userAccessorService.debitPoint(pointToPay);

        Long createdOrderId = service.create(stayPeriod, pointToPay);

        reservationService.reserve(roomId, stayPeriod, createdOrderId);
        service.complete(createdOrderId);

        return new OrderCreateResult(createdOrderId);
    }

    private static boolean isCouponApplicable(Long couponReferenceId, String couponType) {
        return couponReferenceId != null && couponType != null;
    }

    // order
    public OrderCancelResult cancelOrder(Long orderId) {
        OrderCancelResult orderCancelResult = service.cancel(orderId);

        userAccessorService.creditPoint(orderCancelResult.point());

        return orderCancelResult;
    }
}
