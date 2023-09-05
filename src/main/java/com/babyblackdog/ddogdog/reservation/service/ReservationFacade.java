package com.babyblackdog.ddogdog.reservation.service;

import com.babyblackdog.ddogdog.place.PlaceService;
import com.babyblackdog.ddogdog.place.RoomBookingResult;
import com.babyblackdog.ddogdog.reservation.service.dto.StayPeriod;
import com.babyblackdog.ddogdog.reservation.service.dto.result.OrderedReservationResult;
import com.babyblackdog.ddogdog.reservation.service.dto.result.RoomOrderPageResult;
import com.babyblackdog.ddogdog.user.UserService;
import org.springframework.stereotype.Service;

@Service
public class ReservationFacade {

    private final ReservationService service;
    private final PlaceService placeService;
    private final UserService userService;

    public ReservationFacade(ReservationService service,
            PlaceService placeService,
            UserService userService) {
        this.service = service;
        this.placeService = placeService;
        this.userService = userService;
    }

    public RoomOrderPageResult findRoomInfo(Long placeId, Long roomId, StayPeriod stayPeriod) {
        validateStay(roomId, stayPeriod);

        RoomBookingResult roomBookingResult = placeService.findRoomInfo(placeId, roomId);
        return new RoomOrderPageResult(
                roomBookingResult.placeName(),
                roomBookingResult.roomName(), roomBookingResult.point(),
                stayPeriod.checkIn(), stayPeriod.checkOut()
        );
    }

    // 현재 트랜잭션하지 않음
    public OrderedReservationResult order(Long userId,
            Long placeId, Long roomId, StayPeriod stayPeriod) {
        // 유저가 존재하는지 검사
        if (!userService.doesUserExist(userId)) {
            throw new IllegalArgumentException("유저가 존재하지 않습니다.");
        }

        // room의 금액 가져오기
        RoomBookingResult roomInfo = placeService.findRoomInfo(placeId, roomId);

        // 숙박 가능한 지 검사
        validateStay(roomId, stayPeriod);

        // 결제
        if (userService.deductUserPoints(userId, roomInfo.point())) {
            throw new IllegalArgumentException("결제 실패");
        }

        Long reservationId = service.create(userId, roomId, roomInfo,
                stayPeriod.checkIn(), stayPeriod.checkOut());
        return new OrderedReservationResult(reservationId);
    }

    private void validateStay(Long roomId, StayPeriod stayPeriod) {
        if (!service.isRoomAvailableOnDate(roomId, stayPeriod.checkIn(), stayPeriod.checkOut())) {
            throw new IllegalStateException("현재 기간을 예약할 수 없습니다.");
        }
    }
}
