package com.babyblackdog.ddogdog.place.controller.dto;

import com.babyblackdog.ddogdog.place.service.dto.RoomResult;
import org.springframework.data.domain.Page;

public record RoomResponses(Page<RoomResponse> roomResponses) {

    public static RoomResponses of(Page<RoomResult> results) {
        return new RoomResponses(results.map(RoomResponse::of));
    }

}
