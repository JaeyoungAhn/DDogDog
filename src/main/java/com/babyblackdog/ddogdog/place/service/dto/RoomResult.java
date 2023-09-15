package com.babyblackdog.ddogdog.place.service.dto;

import com.babyblackdog.ddogdog.place.model.Room;

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
        long point,
        boolean reservationAvailable
) {

    public static RoomResult of(Room room) {
        boolean notUsingValue = false;
        return new RoomResult(
                room.getId(),
                room.getHotelName(),
                room.getRoomTypeName(),
                room.getDescription(),
                room.getMaxOccupancy(),
                room.isHasBed(),
                room.isHasAmenities(),
                room.isSmokingAvailable(),
                room.getRoomNumber(),
                room.getPoint(),
                notUsingValue
        );
    }

    public static RoomResult of(Room room, boolean reservationAvailable) {
        return new RoomResult(
                room.getId(),
                room.getHotelName(),
                room.getRoomTypeName(),
                room.getDescription(),
                room.getMaxOccupancy(),
                room.isHasBed(),
                room.isHasAmenities(),
                room.isSmokingAvailable(),
                room.getRoomNumber(),
                room.getPoint(),
                reservationAvailable
        );
    }
}
