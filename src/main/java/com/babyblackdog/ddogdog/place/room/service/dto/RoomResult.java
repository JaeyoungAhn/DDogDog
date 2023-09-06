package com.babyblackdog.ddogdog.place.room.service.dto;

import com.babyblackdog.ddogdog.place.room.model.Room;

public record RoomResult(
    Long id,
    String hotelName,
    String roomType,
    String description,
    int occupancy,
    boolean hasBed,
    boolean hasAmenities,
    boolean smokingAvailable,
    String roomNumber,
    long point
) {

  public static RoomResult of(Room room) {
    return new RoomResult(
        room.getId(),
        room.getHotelName(),
        room.getRoomType(),
        room.getDescription(),
        room.getMaxOccupancy(),
        room.isHasBed(),
        room.isHasAmenities(),
        room.isSmokingAvailable(),
        room.getRoomNumber(),
        room.getPoint()
    );
  }
}
