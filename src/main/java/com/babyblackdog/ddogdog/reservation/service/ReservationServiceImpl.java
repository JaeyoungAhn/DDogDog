package com.babyblackdog.ddogdog.reservation.service;

import com.babyblackdog.ddogdog.common.date.TimeProvider;
import com.babyblackdog.ddogdog.order.service.OrderService;
import com.babyblackdog.ddogdog.place.reader.vo.RoomSimpleResult;
import com.babyblackdog.ddogdog.reservation.domain.Reservation;
import com.babyblackdog.ddogdog.reservation.domain.ReservationRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ReservationServiceImpl implements ReservationService {

  private final ReservationRepository repository;
  private final OrderService orderService;
  private final TimeProvider timeProvider;

  public ReservationServiceImpl(ReservationRepository repository, OrderService orderService,
      TimeProvider timeProvider) {
    this.repository = repository;
    this.orderService = orderService;
    this.timeProvider = timeProvider;
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
    Long paymentId = orderService.create(roomInfo.point(), roomInfo.point(),
        timeProvider.getCurrentDate());
    Reservation newReservation = new Reservation(paymentId, userId, roomId, checkIn, checkOut);
    return repository.save(newReservation).getId();
  }
}
