package com.babyblackdog.ddogdog.reservation.service;

import com.babyblackdog.ddogdog.common.date.StayPeriod;
import com.babyblackdog.ddogdog.place.reader.vo.RoomSimpleResult;
import com.babyblackdog.ddogdog.reservation.service.reader.ReservationReaderService;
import java.time.LocalDate;
import java.util.List;

public interface ReservationService extends ReservationReaderService {

  Long create(Long userId, Long roomId, RoomSimpleResult roomInfo,
      LocalDate checkIn, LocalDate checkOut);

    List<Long> reserve(Long roomId, StayPeriod stayPeriod, Long createdOrderId);
}
