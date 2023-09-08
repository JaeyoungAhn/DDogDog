package com.babyblackdog.ddogdog.place.reader.vo;

import com.babyblackdog.ddogdog.place.room.service.dto.RoomResult;

public record RoomSimpleResult(
    String hotelName,
    String roomType,
    String roomDescription,
    String roomNumber,
    long point
) {

  public static RoomSimpleResult of(RoomResult roomResult) {
    return new RoomSimpleResult(
        roomResult.hotelName(),
        roomResult.roomType(),
        roomResult.description(),
        roomResult.roomNumber(),
        roomResult.point()
    );
  }
}
