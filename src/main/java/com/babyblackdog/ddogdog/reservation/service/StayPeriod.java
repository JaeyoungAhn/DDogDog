package com.babyblackdog.ddogdog.reservation.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class StayPeriod {

    private final LocalDate checkIn;
    private final LocalDate checkOut;

    public StayPeriod(LocalDate checkIn, LocalDate checkOut, TimeProvider timeProvider) {
        this.checkIn = adjustIfInvalid(checkIn, timeProvider);
        LocalDate validCheckOut = adjustIfInvalid(checkOut, timeProvider);
        this.checkOut = ensureCheckOutIsAfterCheckIn(this.checkIn, validCheckOut);
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public long getPeriod() {
        return ChronoUnit.DAYS.between(this.checkIn, this.getCheckOut());
    }

    private LocalDate adjustIfInvalid(LocalDate date, TimeProvider timeProvider) {
        if (date == null || timeProvider.getCurrentDate().isAfter(date)) {
            return timeProvider.getCurrentDate();
        }
        return date;
    }

    private LocalDate ensureCheckOutIsAfterCheckIn(LocalDate checkIn, LocalDate checkOut) {
        if (!checkOut.isAfter(checkIn)) {
            return checkIn.plusDays(1);
        }
        return checkOut;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StayPeriod that = (StayPeriod) o;
        return Objects.equals(getCheckIn(), that.getCheckIn()) && Objects.equals(
                getCheckOut(), that.getCheckOut());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCheckIn(), getCheckOut());
    }
}
