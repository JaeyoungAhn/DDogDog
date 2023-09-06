package com.babyblackdog.ddogdog.reservation.service;

import com.babyblackdog.ddogdog.place.room.RoomSimpleResult;
import java.time.LocalDate;

public interface ReservationService {

  /**
   * @return 체크인부터 체크아웃까지의 모든 날이 가능하다면 true, 아니라면 false
   */
  boolean isRoomAvailableOnDate(Long roomId, LocalDate checkIn, LocalDate checkOut);

  Long create(Long userId, Long roomId, RoomSimpleResult roomInfo, LocalDate checkIn,
      LocalDate checkOut);
}
