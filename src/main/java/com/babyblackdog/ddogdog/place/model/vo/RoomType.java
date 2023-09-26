package com.babyblackdog.ddogdog.place.model.vo;

import static com.babyblackdog.ddogdog.global.exception.ErrorCode.INVALID_ROOM_TYPE;

import com.babyblackdog.ddogdog.global.exception.RoomException;
import java.util.Arrays;
import java.util.Objects;

public enum RoomType {
    SINGLE("싱글"),
    TWIN("트윈"),
    DELUXE("디럭스"),
    DOUBLE("더블");

    private final String typeName;

    RoomType(String typeName) {
        this.typeName = typeName;
    }

    public static RoomType nameOf(String roomTypeName) {
        return Arrays.stream(RoomType.values())
                .filter(roomType -> Objects.equals(roomType.typeName, roomTypeName))
                .findAny()
                .orElseThrow(() -> new RoomException(INVALID_ROOM_TYPE));
    }

    public String getTypeName() {
        return typeName;
    }
}
