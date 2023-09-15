package com.babyblackdog.ddogdog.place.controller.dto;

import com.babyblackdog.ddogdog.place.service.dto.RoomResult;

public record RoomResponse(
    Long id,
    String hotelName,
    String roomType,
    String description,
    int occupancy,
    boolean hasBed,
    boolean hasAmenities,
    boolean smokingAvailable,
    String roomNumber,
    long point,
    boolean reservationAvailable
) {

  public static RoomResponse of(RoomResult roomResult) {
    return new RoomResponse(
        roomResult.id(),
        roomResult.hotelName(),
        roomResult.roomType(),
        roomResult.description(),
        roomResult.occupancy(),
        roomResult.hasBed(),
        roomResult.hasAmenities(),
        roomResult.smokingAvailable(),
        roomResult.roomNumber(),
        roomResult.point(),
        roomResult.reservationAvailable()
    );
  }
}
