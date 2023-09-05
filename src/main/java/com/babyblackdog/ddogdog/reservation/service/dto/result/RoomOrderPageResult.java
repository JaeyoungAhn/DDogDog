package com.babyblackdog.ddogdog.reservation.service.dto.result;

import java.time.LocalDate;

public record RoomOrderPageResult(
        String placeName,
        String roomName,
        long roomPoint,
        LocalDate checkIn,
        LocalDate checkOut) {

}
