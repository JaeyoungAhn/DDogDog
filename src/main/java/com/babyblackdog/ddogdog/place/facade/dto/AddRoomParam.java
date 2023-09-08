package com.babyblackdog.ddogdog.place.facade.dto;

import com.babyblackdog.ddogdog.common.point.Point;
import com.babyblackdog.ddogdog.place.hotel.model.Hotel;
import com.babyblackdog.ddogdog.place.room.model.Room;
import com.babyblackdog.ddogdog.place.room.model.vo.Occupancy;
import com.babyblackdog.ddogdog.place.room.model.vo.RoomNumber;
import com.babyblackdog.ddogdog.place.room.model.vo.RoomType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddRoomParam(
    @NotNull
    RoomType roomType,
    @NotNull
    Long hotelId,
    @NotBlank
    String description,
    @NotNull
    Occupancy occupancy,
    boolean hasBed,
    boolean hasAmenities,
    boolean smokingAvailable,
    @NotNull
    RoomNumber roomNumber,
    @NotNull
    Point point
) {

    public static Room to(Hotel hotel, AddRoomParam addRoomParam) {
        return new Room(
            hotel,
            addRoomParam.roomType,
            addRoomParam.description,
            addRoomParam.occupancy,
            addRoomParam.hasBed,
            addRoomParam.hasAmenities,
            addRoomParam.smokingAvailable,
            addRoomParam.roomNumber,
            addRoomParam.point
        );
    }
}
