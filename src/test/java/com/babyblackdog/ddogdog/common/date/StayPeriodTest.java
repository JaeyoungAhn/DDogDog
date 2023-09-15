package com.babyblackdog.ddogdog.common.date;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StayPeriodTest {

    private static final LocalDate FIXED_DATE = LocalDate.now();
    private final TimeProvider timeProvider = new FixedTimeProvider(FIXED_DATE);

    @Test
    @DisplayName("체크인과 체크아웃이 null이거나 고정된 값보다 작은 경우, 고정된 날짜 기준으로 하루 차이나게 설정한다.")
    void create_SetValidDate() {
        // Given
        LocalDate checkIn = null;
        LocalDate checkOut = null;

        // When
        StayPeriod actual = new StayPeriod(checkIn, checkOut, timeProvider);

        // Then
        assertThat(actual.getCheckIn()).isEqualTo(FIXED_DATE);
        assertThat(actual.getCheckOut()).isEqualTo(FIXED_DATE.plusDays(1));
        assertThat(actual.getPeriod()).isEqualTo(1);
    }


    @Test
    @DisplayName("체크인만 null이거나 고정된 값보다 작은 경우, 체크인을 고정된 날짜로 설정한다.")
    void create_SetCheckInToFixedDate() {
        // Given
        LocalDate checkIn = null;
        LocalDate checkOut = FIXED_DATE.plusDays(5);

        // When
        StayPeriod actual = new StayPeriod(checkIn, checkOut, timeProvider);

        // Then
        assertThat(actual.getCheckIn()).isEqualTo(FIXED_DATE);
        assertThat(actual.getCheckOut()).isEqualTo(checkOut);
        assertThat(actual.getPeriod()).isEqualTo(5);
    }

    @Test
    @DisplayName("체크인보다 체크아웃이 작은 경우, 체크인 다음 날로 설정한다.")
    void create_SetCheckOutToAfterCheckIn() {
        // Given
        LocalDate checkIn = FIXED_DATE.plusDays(5);
        LocalDate checkOut = FIXED_DATE.plusDays(3);

        LocalDate expectedCheckOut = checkIn.plusDays(1);

        // When
        StayPeriod actual = new StayPeriod(checkIn, checkOut, timeProvider);

        // Then
        assertThat(actual.getCheckIn()).isEqualTo(checkIn);
        assertThat(actual.getCheckOut()).isEqualTo(expectedCheckOut);
        assertThat(actual.getPeriod()).isEqualTo(1);
    }

    @Test
    @DisplayName("체크인과 체크아웃이 유효한 값일 경우, 그대로 생성한다.")
    void create_CreateRow() {
        // Given
        LocalDate checkIn = FIXED_DATE.plusDays(3);
        LocalDate checkOut = FIXED_DATE.plusDays(5);

        // When
        StayPeriod actual = new StayPeriod(checkIn, checkOut, timeProvider);

        // Then
        assertThat(actual.getCheckIn()).isEqualTo(checkIn);
        assertThat(actual.getCheckOut()).isEqualTo(checkOut);
        assertThat(actual.getPeriod()).isEqualTo(5 - 3);
    }
}
