package com.babyblackdog.ddogdog.place.room.controller.dto;

import com.babyblackdog.ddogdog.common.point.Point;
import com.babyblackdog.ddogdog.place.facade.dto.AddRoomParam;
import com.babyblackdog.ddogdog.place.room.model.vo.Occupancy;
import com.babyblackdog.ddogdog.place.room.model.vo.RoomNumber;
import com.babyblackdog.ddogdog.place.room.model.vo.RoomType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record AddRoomRequest(
    @NotBlank
    String roomType,
    @NotBlank
    String description,
    @Positive
    int occupancy,
    boolean hasBed,
    boolean hasAmenities,
    boolean smokingAvailable,
    @NotBlank
    String roomNumber,
    @Positive
    long point
) {

  public static AddRoomParam to(Long hotelId, AddRoomRequest request) {
    return new AddRoomParam(
        RoomType.nameOf(request.roomType),
        hotelId,
        request.description,
        new Occupancy(request.occupancy),
        request.hasBed,
        request.hasAmenities,
        request.smokingAvailable,
        new RoomNumber(request.roomNumber),
        new Point(request.point)
    );
  }

}
