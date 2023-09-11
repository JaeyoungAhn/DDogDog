package com.babyblackdog.ddogdog.order.controller.dto.response;

import com.babyblackdog.ddogdog.order.service.dto.result.RoomOrderPageResult;
import java.time.LocalDate;

public record RoomOrderPageResponse(
    String placeName,
    String roomName,
    long stayCost,
    LocalDate checkIn,
    LocalDate checkOut
) {

  public static RoomOrderPageResponse of(RoomOrderPageResult result) {
    return new RoomOrderPageResponse(
        result.placeName(),
        result.roomName(),
        result.stayPoint(),
        result.checkIn(),
        result.checkOut()
    );
  }
}
