package com.babyblackdog.ddogdog.reservation.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.babyblackdog.ddogdog.global.exception.ErrorCode;
import com.babyblackdog.ddogdog.global.exception.ReserveException;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ReservationTest {

    @Nested
    class Constructor {

        @Test
        @DisplayName("유효한 값이라면 객체를 생성한다.")
        void validConstructor() {
            Long roomId = 1L;
            LocalDate date = LocalDate.now();

            Reservation reservation = new Reservation(roomId, date);

            assertThat(reservation).isNotNull();
        }

        @Test
        @DisplayName("1개라도 null이라면 예외를 발생시킨다.")
        void invalidConstructor() {
            Long roomId = null;
            LocalDate date = null;

            assertThatThrownBy(() -> new Reservation(roomId, LocalDate.now()))
                    .isInstanceOf(NullPointerException.class)
                    .hasMessageContaining("roomId는 Null일 수 없습니다.");

            assertThatThrownBy(() -> new Reservation(1L, date))
                    .isInstanceOf(NullPointerException.class)
                    .hasMessageContaining("reservationDate는 Null일 수 없습니다.");
        }
    }

    @Nested
    class ReserveMethod {

        @Test
        @DisplayName("예약이 아직 안된 상태라면 예약을 할 수 있다.")
        void canReserve() {
            Long roomId = 1L;
            LocalDate date = LocalDate.now();
            Reservation reservation = new Reservation(roomId, date);

            reservation.reserve(2L);

            assertThat(reservation.isReserved()).isTrue();
        }

        @Test
        @DisplayName("이미 예약된 상태라면 예외를 발생시킨다.")
        void cannotReserve() {
            Long roomId = 1L;
            LocalDate date = LocalDate.now();
            Reservation reservation = new Reservation(roomId, date);

            reservation.reserve(2L);

            assertThatThrownBy(() -> reservation.reserve(3L))
                    .isInstanceOf(ReserveException.class)
                    .hasMessageContaining(ErrorCode.ALREADY_RESERVED.getMessage());
        }
    }

    @Test
    @DisplayName("예약이 안된 상태라면 isAvailable은 true를 반환한다.")
    void isAvailableReturnsTrue() {
        Long roomId = 1L;
        LocalDate date = LocalDate.now();
        Reservation reservation = new Reservation(roomId, date);

        assertThat(reservation.isAvailable()).isTrue();
    }

    @Test
    @DisplayName("예약된 상태라면 isAvailable은 false를 반환한다.")
    void isAvailableReturnsFalse() {
        Long roomId = 1L;
        LocalDate date = LocalDate.now();
        Reservation reservation = new Reservation(roomId, date);

        reservation.reserve(2L);

        assertThat(reservation.isAvailable()).isFalse();
    }
}
