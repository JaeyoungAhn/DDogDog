package com.babyblackdog.ddogdog.order.service.dto.result;

import java.time.LocalDate;

public record RoomOrderPageResult(
    String placeName,
    String roomName,
    String roomDescription,
    String roomNumber,
    long stayCost,
    LocalDate checkIn,
    LocalDate checkOut) {

}
