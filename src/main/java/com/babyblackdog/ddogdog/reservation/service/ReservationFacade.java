package com.babyblackdog.ddogdog.reservation.service;

import com.babyblackdog.ddogdog.common.date.StayPeriod;
import com.babyblackdog.ddogdog.common.point.Point;
import com.babyblackdog.ddogdog.place.reader.PlaceReaderService;
import com.babyblackdog.ddogdog.place.reader.vo.RoomSimpleResult;
import com.babyblackdog.ddogdog.reservation.service.dto.result.OrderedReservationResult;
import com.babyblackdog.ddogdog.reservation.service.dto.result.RoomOrderPageResult;
import com.babyblackdog.ddogdog.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReservationFacade {

  private final ReservationService service;
  private final PlaceReaderService placeService;
  private final UserService userService;

  public ReservationFacade(
      ReservationService service,
      PlaceReaderService placeService,
      UserService userService
  ) {
    this.service = service;
    this.placeService = placeService;
    this.userService = userService;
  }

    public ReservationFacade(ReservationService service, HotelService hotelService,
            UserService userService, TimeProvider timeProvider) {
        this.service = service;
        this.hotelService = hotelService;
        this.userService = userService;
    }

    RoomSimpleResult roomSimpleResult = placeService.findRoomSimpleInfo(roomId);
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

        RoomSimpleResult roomSimpleResult = hotelService.findRoomInfo(placeId, roomId);
        return new RoomOrderPageResult(
                roomSimpleResult.hotelName(),
                roomSimpleResult.roomName(),
                roomSimpleResult.roomDescription(),
                roomSimpleResult.point(),
                stayPeriod.checkIn(),
                stayPeriod.checkOut()
        );
    }

    // room의 금액 가져오기
    RoomSimpleResult roomInfo = placeService.findRoomSimpleInfo(roomId);

        // room의 금액 가져오기
        RoomSimpleResult roomInfo = hotelService.findRoomInfo(placeId, roomId);

        // 숙박 가능한 지 검사
        validateStay(roomId, stayPeriod);

        // 결제
        if (userService.deductUserPoints(userId, new Point(roomInfo.point()))) {
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
