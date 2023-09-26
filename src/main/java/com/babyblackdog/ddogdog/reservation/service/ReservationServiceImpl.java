package com.babyblackdog.ddogdog.reservation.service;

import com.babyblackdog.ddogdog.global.exception.ErrorCode;
import com.babyblackdog.ddogdog.global.exception.UnreservableDateException;
import com.babyblackdog.ddogdog.reservation.domain.Reservation;
import com.babyblackdog.ddogdog.reservation.domain.ReservationRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository repository;
    private final TimeProvider timeProvider;

    public ReservationServiceImpl(ReservationRepository repository, TimeProvider timeProvider) {
        this.repository = repository;
        this.timeProvider = timeProvider;
    }

    @Override
    public boolean isRoomAvailableOnDate(Long roomId, LocalDate checkIn, LocalDate checkOut) {
        List<Reservation> reservationList = findReservations(roomId, checkIn, checkOut);

        long period = new StayPeriod(checkIn, checkOut, timeProvider).getPeriod();
        return reservationList.size() == period && areAllDatesAvailable(reservationList);
    }

    @Override
    @Transactional
    public List<Long> reserve(Long roomId, StayPeriod stayPeriod, Long orderId) {
        List<Reservation> reservationList = findReservations(roomId, stayPeriod.getCheckIn(), stayPeriod.getCheckOut());

        if (reservationList.size() != stayPeriod.getPeriod() || !areAllDatesAvailable(reservationList)) {
            throw new UnreservableDateException(ErrorCode.UNRESERVED_PERIOD);
        }

        reservationList.forEach(reservation -> reservation.reserve(orderId));

        return reservationList.stream()
                .map(Reservation::getId)
                .toList();
    }

    private List<Reservation> findReservations(Long roomId, LocalDate checkIn, LocalDate checkOut) {
        StayPeriod reservationPeriod = new StayPeriod(checkIn, checkOut, timeProvider);
        return repository.findReservationsByDateRange(roomId,
                reservationPeriod.getCheckIn(), reservationPeriod.getCheckOut());
    }

    private boolean areAllDatesAvailable(List<Reservation> reservations) {
        return reservations.stream().allMatch(Reservation::isAvailable);
    }
}
