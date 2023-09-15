package com.babyblackdog.ddogdog.place.accessor.vo;

import com.babyblackdog.ddogdog.common.point.Point;
import com.babyblackdog.ddogdog.place.service.dto.RoomResult;

public record RoomSimpleResult(
        String hotelName,
        String roomType,
        String roomDescription,
        String roomNumber,
        Point point
) {

    public static RoomSimpleResult of(RoomResult roomResult) {
        return new RoomSimpleResult(
                roomResult.hotelName(),
                roomResult.roomType(),
                roomResult.description(),
                roomResult.roomNumber(),
                new Point(roomResult.point())
        );
    }
}
