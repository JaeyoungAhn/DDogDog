package com.babyblackdog.ddogdog.order.service.dto.result;

import com.babyblackdog.ddogdog.common.point.Point;
import java.time.LocalDate;

public record RoomOrderPageResult(
    String placeName,
    String roomName,
    String roomDescription,
    String roomNumber,
    Point stayCost,
    LocalDate checkIn,
    LocalDate checkOut) {

}
