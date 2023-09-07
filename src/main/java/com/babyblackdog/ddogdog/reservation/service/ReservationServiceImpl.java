package com.babyblackdog.ddogdog.reservation.service;

import com.babyblackdog.ddogdog.common.Point;
import com.babyblackdog.ddogdog.place.room.RoomSimpleResult;
import com.babyblackdog.ddogdog.reservation.domain.Payment;
import com.babyblackdog.ddogdog.reservation.domain.Reservation;
import com.babyblackdog.ddogdog.reservation.domain.ReservationRepository;
import com.babyblackdog.ddogdog.reservation.service.reader.ReservationReaderService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ReservationServiceImpl implements ReservationService {

  private final ReservationRepository repository;

  public ReservationServiceImpl(ReservationRepository repository) {
    this.repository = repository;
  }

  @Override
  public boolean isRoomAvailableOnDate(Long roomId, LocalDate checkIn, LocalDate checkOut) {
    // 비즈니스 로직으로 뺄 수 있음
    List<Reservation> overlappingReservationList = repository.findOverlappingReservations(
            roomId,
            checkIn, checkOut);

    return overlappingReservationList.isEmpty();
  }

  @Override
  public Long create(Long userId, Long roomId, RoomSimpleResult roomInfo,
      LocalDate checkIn, LocalDate checkOut) {
    Reservation newReservation = new Reservation(
        new Payment(new Point(roomInfo.point()), new Point(roomInfo.point()), LocalDate.now()),
        userId, roomId, checkIn, checkOut
    );
    return repository.save(newReservation).getId();
  }
}
