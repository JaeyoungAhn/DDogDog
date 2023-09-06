package com.babyblackdog.ddogdog.reservation.service.dto.result;

import java.time.LocalDate;

public record RoomOrderPageResult(
    String placeName,
    String roomName,
    String roomDescription,
    String roomNumber,
    long roomPoint,
    LocalDate checkIn,
    LocalDate checkOut) {

}
