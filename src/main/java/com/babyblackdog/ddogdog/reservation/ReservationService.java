package com.babyblackdog.ddogdog.reservation;

import java.time.LocalDate;

public interface ReservationService {

    /**
     * @return 체크인부터 체크아웃까지의 모든 날이 가능하다면 true, 아니라면 false
     */
    boolean isRoomAvailableOnDate(Long roomId, LocalDate checkIn, LocalDate checkOut);

    /**
     * 특정 reservationId를 userId가 정상적으로 가지고 있는지
     * @return 예약 아이디가 있고, 그 예약 아이디에 유저가 있다면 true, 아니라면 false
     */
    boolean isReservationForUser(Long reservationId, Long userId);
}
