package com.babyblackdog.ddogdog.order.service;

import com.babyblackdog.ddogdog.common.date.StayPeriod;
import com.babyblackdog.ddogdog.common.point.Point;
import com.babyblackdog.ddogdog.order.service.dto.result.OrderCreateResult;
import com.babyblackdog.ddogdog.place.reader.PlaceReaderService;
import com.babyblackdog.ddogdog.place.reader.vo.RoomSimpleResult;
import com.babyblackdog.ddogdog.reservation.service.ReservationService;
import com.babyblackdog.ddogdog.reservation.service.dto.result.OrderedReservationResult;
import com.babyblackdog.ddogdog.order.service.dto.result.RoomOrderPageResult;
import com.babyblackdog.ddogdog.user.service.UserService;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderFacade {

    private final OrderService service;
    private final PlaceReaderService placeReaderService;
    private final ReservationService reservationService;
    private final UserService userService;

    public OrderFacade(OrderService service, PlaceReaderService placeReaderService,
            ReservationService reservationService, UserService userService) {
        this.service = service;
        this.placeReaderService = placeReaderService;
        this.reservationService = reservationService;
        this.userService = userService;
    }

    public RoomOrderPageResult findRoomSummary(Long roomId, StayPeriod stayPeriod) {
        RoomSimpleResult roomSimpleResult = placeReaderService.findRoomSimpleInfo(roomId);

        long stayDays = ChronoUnit.DAYS.between(stayPeriod.checkOut(), stayPeriod.checkIn());
        return new RoomOrderPageResult(
                roomSimpleResult.hotelName(),
                roomSimpleResult.roomType(),
                roomSimpleResult.roomDescription(),
                roomSimpleResult.roomNumber(),
                roomSimpleResult.point() * stayDays,
                stayPeriod.checkIn(),
                stayPeriod.checkOut()
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

        // 숙박 가능 여부
        List<Long> selectedReservationIdList = lockTableReservationForUpdate(roomId, stayPeriod);

        // 결제
        if (userService.deductUserPoints(userId, new Point(roomInfo.point()))) {
            throw new IllegalArgumentException("결제 실패");
        }
        Long reservationId = service.create(userId, roomId, roomInfo,
                stayPeriod.checkIn(), stayPeriod.checkOut());

        // 예약 상태 변경

        return new OrderedReservationResult(reservationId);
    }
}
