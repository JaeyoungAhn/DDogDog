package com.babyblackdog.ddogdog.reservation.service;

import com.babyblackdog.ddogdog.place.RoomBookingResult;
import com.babyblackdog.ddogdog.reservation.domain.Payment;
import com.babyblackdog.ddogdog.reservation.domain.Reservation;
import com.babyblackdog.ddogdog.reservation.domain.ReservationRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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
    public boolean isReservationForUser(Long reservationId, Long userId) {
        Optional<Reservation> foundReservaionOptional = repository.findById(reservationId);

        return foundReservaionOptional.map(reservation -> reservation.matchByUserId(userId))
                .orElse(false);
    }

    @Override
    public Long create(Long userId, Long roomId, RoomBookingResult roomInfo,
            LocalDate checkIn, LocalDate checkOut) {
        Reservation newReservation = new Reservation(
                new Payment(roomInfo.point(), roomInfo.point(), LocalDate.now()),
                userId, roomId, checkIn, checkOut
        );
        return repository.save(newReservation).getId();
    }
}
