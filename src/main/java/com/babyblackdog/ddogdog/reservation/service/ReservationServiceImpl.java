package com.babyblackdog.ddogdog.reservation.service;

import com.babyblackdog.ddogdog.common.date.StayPeriod;
import com.babyblackdog.ddogdog.reservation.domain.Reservation;
import com.babyblackdog.ddogdog.reservation.domain.ReservationRepository;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository repository;

    public ReservationServiceImpl(ReservationRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean isRoomAvailableOnDate(Long roomId, LocalDate checkIn, LocalDate checkOut) {
        List<Reservation> reservationList =
                repository.findReservationsByDateRange(roomId, checkIn, checkOut);

        if (ChronoUnit.DAYS.between(checkOut, checkIn)
                != reservationList.size()) {
            return false;
        }

        for (Reservation reservation : reservationList) {
            if (reservation.isReserved()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Long create(Long roomId, LocalDate reservationDate) {
        Reservation savedReservation = repository.save(new Reservation(roomId, reservationDate));
        return savedReservation.getId();
    }

    @Override
    public List<Long> reserve(Long roomId, StayPeriod stayPeriod, Long orderId) {
        List<Reservation> reservationList =
                repository.findReservationsByDateRange(roomId, stayPeriod.getCheckIn(),
                        stayPeriod.getCheckOut());

        if (ChronoUnit.DAYS.between(stayPeriod.getCheckIn(), stayPeriod.getCheckOut())
                != reservationList.size()) {
            throw new IllegalStateException("예약할 수 없는 날짜가 포함되어 있습니다.");
        }

        for (Reservation reservation : reservationList) {
            reservation.reserve(orderId);
        }

        return reservationList.stream()
                .map(Reservation::getId)
                .toList();
    }
}
