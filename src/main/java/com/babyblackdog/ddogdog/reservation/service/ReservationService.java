package com.babyblackdog.ddogdog.reservation.service;

import com.babyblackdog.ddogdog.reservation.service.reader.ReservationReaderService;
import java.time.LocalDate;
import java.util.List;

public interface ReservationService extends ReservationReaderService {

    Long create(Long roomId, LocalDate reservationDate);

    List<Long> reserve(Long roomId, StayPeriod stayPeriod, Long orderId);
}
