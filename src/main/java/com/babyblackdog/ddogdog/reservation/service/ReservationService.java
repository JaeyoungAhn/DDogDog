package com.babyblackdog.ddogdog.reservation.service;

import com.babyblackdog.ddogdog.place.room.RoomSimpleResult;
import com.babyblackdog.ddogdog.reservation.service.reader.ReservationReaderService;
import java.time.LocalDate;

public interface ReservationService extends ReservationReaderService {

  Long create(Long userId, Long roomId, RoomSimpleResult roomInfo, LocalDate checkIn,
      LocalDate checkOut);
}
