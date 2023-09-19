package com.babyblackdog.ddogdog.reservation.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import com.babyblackdog.ddogdog.global.exception.ErrorCode;
import com.babyblackdog.ddogdog.global.exception.UnreservableDateException;
import com.babyblackdog.ddogdog.reservation.domain.Reservation;
import com.babyblackdog.ddogdog.reservation.domain.ReservationRepository;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@DisplayName("예약 테스트")
@SpringBootTest
class ReservationServiceImplTest {

    @Autowired
    private ReservationServiceImpl reservationServiceImpl;

    @MockBean
    private ReservationRepository reservationRepository;

    @Autowired
    private TimeProvider timeProvider;

    @Nested
    class IsRoomAvailableOnDateMethod {

        @DisplayName("예약 날짜가 존재하지 않는다면 실패한다.")
        @Test
        void withoutDatesReservation_failure() {
            // Given
            long roomId = 1L;
            long period = 2L;
            LocalDate checkIn = timeProvider.getCurrentDate();
            LocalDate checkOut = timeProvider.getCurrentDate().plusDays(period);

            List<Reservation> reservationList = new ArrayList<>();
            for (long iDay = 0; iDay < ChronoUnit.DAYS.between(checkIn, checkOut); iDay += 1) {
                reservationList.add(new Reservation(roomId, checkIn.plusDays(iDay)));
            }
            reservationList.remove(0);

            given(reservationRepository.findReservationsByDateRange(roomId, checkIn, checkOut)).willReturn(
                    (reservationList));

            // When
            boolean result = reservationServiceImpl.isRoomAvailableOnDate(1L, LocalDate.now(),
                    LocalDate.now().plusDays(period));

            // Then
            assertThat(result).isFalse();
        }

        @DisplayName("예약된 날짜가 포함되어 있다면 실패한다.")
        @Test
        void includesReservedDatesLeads_failure() {
            // Given
            long roomId = 1L;
            long period = 2L;
            LocalDate checkIn = timeProvider.getCurrentDate();
            LocalDate checkOut = timeProvider.getCurrentDate().plusDays(period);

            List<Reservation> reservationList = new ArrayList<>();
            for (long iDay = 0; iDay < ChronoUnit.DAYS.between(checkIn, checkOut); iDay += 1) {
                reservationList.add(new Reservation(roomId, checkIn.plusDays(iDay)));
            }
            long orderId = 1L;
            reservationList.get(0).reserve(orderId);

            given(reservationRepository.findReservationsByDateRange(roomId, checkIn, checkOut)).willReturn(
                    (reservationList));

            // When
            boolean result = reservationServiceImpl.isRoomAvailableOnDate(1L, LocalDate.now(),
                    LocalDate.now().plusDays(period));

            // Then
            assertThat(result).isFalse();
        }

        @DisplayName("예약 가능한 기간이라면 성공한다.")
        @Test
        void success() {
            // Given
            long roomId = 1L;
            long period = 2L;
            LocalDate checkIn = timeProvider.getCurrentDate();
            LocalDate checkOut = timeProvider.getCurrentDate().plusDays(period);

            List<Reservation> reservationList = new ArrayList<>();
            for (long iDay = 0; iDay < ChronoUnit.DAYS.between(checkIn, checkOut); iDay += 1) {
                reservationList.add(new Reservation(roomId, checkIn.plusDays(iDay)));
            }

            given(reservationRepository.findReservationsByDateRange(roomId, checkIn, checkOut)).willReturn(
                    (reservationList));

            // When
            boolean result = reservationServiceImpl.isRoomAvailableOnDate(1L, LocalDate.now(),
                    LocalDate.now().plusDays(1));

            // Then
            assertThat(result).isFalse();
        }
    }

    @Nested
    class ReserveMethod {

        @DisplayName("예약한 날짜 중 예약이 불가능한 날짜가 포함 되어 있다면 예외가 발생한다.")
        @Test
        void includesUnreservableDates_throwsException() {
            // Given
            long roomId = 1L;

            long period = 2L;
            LocalDate checkIn = timeProvider.getCurrentDate();
            LocalDate checkOut = timeProvider.getCurrentDate().plusDays(period);
            StayPeriod stayPeriod = new StayPeriod(checkIn, checkOut, timeProvider);

            long orderId = 1;

            List<Reservation> reservationList = new ArrayList<>();
            for (long iDay = 0; iDay < ChronoUnit.DAYS.between(checkIn, checkOut); iDay += 1) {
                reservationList.add(new Reservation(roomId, checkIn.plusDays(iDay)));
            }
            reservationList.get(0).reserve(orderId);

            given(reservationRepository.findReservationsByDateRange(roomId, checkIn, checkOut)).willReturn(
                    (reservationList));

            // When & Then
            assertThatThrownBy(() -> {
                reservationServiceImpl.reserve(roomId, stayPeriod, orderId);
            })
                    .isInstanceOf(UnreservableDateException.class)
                    .describedAs(ErrorCode.UNRESERVED_PERIOD.getMessage());
        }

        @DisplayName("기간을 모두 예약할 수 있다면 성공한다.")
        @Test
        void success() {
            // Given
            long roomId = 1L;

            long period = 2L;
            LocalDate checkIn = timeProvider.getCurrentDate();
            LocalDate checkOut = timeProvider.getCurrentDate().plusDays(period);
            StayPeriod stayPeriod = new StayPeriod(checkIn, checkOut, timeProvider);

            long orderId = 1;

            List<Reservation> reservationList = new ArrayList<>();
            for (long iDay = 0; iDay < ChronoUnit.DAYS.between(checkIn, checkOut); iDay += 1) {
                reservationList.add(new Reservation(roomId, checkIn.plusDays(iDay)));
            }

            given(reservationRepository.findReservationsByDateRange(roomId, checkIn, checkOut)).willReturn(
                    (reservationList));

            // When & Then

            assertThatCode(() -> reservationServiceImpl.reserve(roomId, stayPeriod, orderId))
                    .doesNotThrowAnyException();
        }
    }
}
