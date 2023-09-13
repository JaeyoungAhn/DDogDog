package com.babyblackdog.ddogdog.order.service;

import com.babyblackdog.ddogdog.common.StayCostEstimator;
import com.babyblackdog.ddogdog.common.date.StayPeriod;
import com.babyblackdog.ddogdog.common.point.Point;
import com.babyblackdog.ddogdog.order.service.dto.result.OrderCreateResult;
import com.babyblackdog.ddogdog.order.service.dto.result.RoomOrderPageResult;
import com.babyblackdog.ddogdog.place.reader.PlaceReaderService;
import com.babyblackdog.ddogdog.place.reader.vo.RoomSimpleResult;
import com.babyblackdog.ddogdog.reservation.service.ReservationService;
import com.babyblackdog.ddogdog.user.service.UserService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderFacade {

    private final OrderService service;
    private final PlaceReaderService placeReaderService;
    private final ReservationService reservationService;
    private final UserService userService;
    private final StayCostEstimator stayCostEstimator;

    public OrderFacade(OrderService service, PlaceReaderService placeReaderService,
            ReservationService reservationService, UserService userService,
            StayCostEstimator stayCostEstimator) {
        this.service = service;
        this.placeReaderService = placeReaderService;
        this.reservationService = reservationService;
        this.userService = userService;
        this.stayCostEstimator = stayCostEstimator;
    }

    public RoomOrderPageResult findRoomSummary(Long roomId, StayPeriod stayPeriod) {
        RoomSimpleResult roomSimpleResult = placeReaderService.findRoomSimpleInfo(roomId);

        return new RoomOrderPageResult(
                roomSimpleResult.hotelName(),
                roomSimpleResult.roomType(),
                roomSimpleResult.roomDescription(),
                roomSimpleResult.roomNumber(),
                stayCostEstimator.calculateTotalCost(stayPeriod,
                        new Point(roomSimpleResult.point())),
                stayPeriod.getCheckIn(),
                stayPeriod.getCheckOut()
        );
    }

    @Transactional
    public OrderCreateResult order(Long userId, Long roomId, StayPeriod stayPeriod) {
        // 유저 존재 검사
        if (!userService.doesUserExist(userId)) {
            throw new IllegalArgumentException("유저가 존재하지 않습니다.");
        }

        // room의 정보 가져오기
        RoomSimpleResult roomInfo = placeReaderService.findRoomSimpleInfo(roomId);

        // 결제
        Point pointToPay = stayCostEstimator.calculateTotalCost(stayPeriod,
                new Point(roomInfo.point()));
        if (userService.deductUserPoints(userId, pointToPay)) {
            throw new IllegalArgumentException("결제 실패");
        }

        Long createdOrderId = service.create(userId, stayPeriod, pointToPay);

        List<Long> reservedRoomDate =
                reservationService.reserve(roomId, stayPeriod, createdOrderId);
        service.complete(createdOrderId);
        return new OrderCreateResult(createdOrderId);
    }
}
