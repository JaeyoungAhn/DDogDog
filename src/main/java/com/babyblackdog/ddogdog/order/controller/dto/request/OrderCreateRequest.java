package com.babyblackdog.ddogdog.order.controller.dto.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record OrderCreateRequest(
        @NotNull(message = "숙소 번호를 넣어주세요.") Long placeId,
        @NotNull(message = "객실 번호를 넣어주세요.") Long roomId,
        LocalDate checkIn,
        LocalDate checkOut
) {

}
