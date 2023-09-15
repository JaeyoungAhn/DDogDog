package com.babyblackdog.ddogdog.reservation.service;

import com.babyblackdog.ddogdog.common.date.StayPeriod;
import com.babyblackdog.ddogdog.common.date.TimeProvider;
import com.babyblackdog.ddogdog.payment.service.PaymentService;
import com.babyblackdog.ddogdog.place.accessor.vo.RoomSimpleResult;
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
        StayPeriod reservationPeriod = new StayPeriod(checkIn, checkOut, timeProvider);
        List<Reservation> reservationList =
                repository.findReservationsByDateRange(roomId,
                        reservationPeriod.getCheckIn(), reservationPeriod.getCheckOut());

        if (reservationPeriod.getPeriod() != reservationList.size()) {
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

        if (stayPeriod.getPeriod() != reservationList.size()) {
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
