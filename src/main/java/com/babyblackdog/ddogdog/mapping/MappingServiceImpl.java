package com.babyblackdog.ddogdog.mapping;

import com.babyblackdog.ddogdog.reservation.service.ReservationService;
import java.time.LocalDate;
import org.springframework.stereotype.Service;

@Service
public class MappingServiceImpl implements MappingService {

  private final ReservationService reservationService;

  public MappingServiceImpl(ReservationService reservationService) {
    this.reservationService = reservationService;
  }

  @Override
  public boolean isReservationAvailableForRoom(Long roomId, LocalDate checkIn, LocalDate checkOut) {
    return reservationService.isRoomAvailableOnDate(roomId, checkIn, checkOut);
  }
}
