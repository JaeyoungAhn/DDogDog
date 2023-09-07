package com.babyblackdog.ddogdog.reservation.service.dto;

import com.babyblackdog.ddogdog.common.TimeProvider;
import java.time.LocalDate;

public record StayPeriod(LocalDate checkIn, LocalDate checkOut, TimeProvider timeProvider) {

  public StayPeriod {
    checkIn = adjustIfInvalid(checkIn, timeProvider);
    checkOut = adjustIfInvalid(checkOut, timeProvider);
    checkOut = ensureCheckOutIsAfterCheckIn(checkIn, checkOut);
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
}
