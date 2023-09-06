package com.babyblackdog.ddogdog.reservation.service.dto.result;

import com.babyblackdog.ddogdog.common.Point;
import java.time.LocalDate;

public record RoomOrderPageResult(
        String placeName,
        String roomName,
        Point roomPoint,
        LocalDate checkIn,
        LocalDate checkOut) {

}
