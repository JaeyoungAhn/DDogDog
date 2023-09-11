package com.babyblackdog.ddogdog.order.service;

import com.babyblackdog.ddogdog.common.date.StayPeriod;
import com.babyblackdog.ddogdog.place.reader.PlaceReaderService;
import com.babyblackdog.ddogdog.place.reader.vo.RoomSimpleResult;
import com.babyblackdog.ddogdog.reservation.service.dto.result.RoomOrderPageResult;
import com.babyblackdog.ddogdog.reservation.service.reader.ReservationReaderService;
import org.springframework.stereotype.Service;

@Service
public class OrderFacade {

    private final PlaceReaderService placeReaderService;

    private final ReservationReaderService reservationReaderService;

    public OrderFacade(PlaceReaderService placeReaderService,
            ReservationReaderService reservationReaderService) {
        this.placeReaderService = placeReaderService;
        this.reservationReaderService = reservationReaderService;
    }

    public RoomOrderPageResult findRoomSummary(Long roomId, StayPeriod stayPeriod) {
        validateStay(roomId, stayPeriod);

        RoomSimpleResult roomSimpleResult = placeReaderService.findRoomSimpleInfo(roomId);
        return new RoomOrderPageResult(
                roomSimpleResult.hotelName(),
                roomSimpleResult.roomType(),
                roomSimpleResult.roomDescription(),
                roomSimpleResult.roomNumber(),
                roomSimpleResult.point(),
                stayPeriod.checkIn(),
                stayPeriod.checkOut()
        );
    }

    private void validateStay(Long roomId, StayPeriod stayPeriod) {
        if (!reservationReaderService.isRoomAvailableOnDate(roomId, stayPeriod.checkIn(),
                stayPeriod.checkOut())) {
            throw new IllegalStateException("현재 기간을 예약할 수 없습니다.");
        }
    }
}
