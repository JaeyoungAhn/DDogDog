package com.babyblackdog.ddogdog.reservation.service;

import com.babyblackdog.ddogdog.place.reader.vo.RoomSimpleResult;
import java.time.LocalDate;

public interface ReservationService extends ReservationReaderService {

    Long create(Long userId, Long roomId, RoomSimpleResult roomInfo,
            LocalDate checkIn, LocalDate checkOut);
}
