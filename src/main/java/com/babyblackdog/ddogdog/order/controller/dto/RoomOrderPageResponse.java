package com.babyblackdog.ddogdog.order.controller.dto;

import com.babyblackdog.ddogdog.reservation.service.dto.result.RoomOrderPageResult;
import java.time.LocalDate;

public record RoomOrderPageResponse(
    String placeName,
    String roomName,
    long roomPoint,
    LocalDate checkIn,
    LocalDate checkOut
) {

  public static RoomOrderPageResponse of(RoomOrderPageResult result) {
    return new RoomOrderPageResponse(
        result.placeName(),
        result.roomName(),
        result.roomPoint(),
        result.checkIn(),
        result.checkOut()
    );
  }
}
