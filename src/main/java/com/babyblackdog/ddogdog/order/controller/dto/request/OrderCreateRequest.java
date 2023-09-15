package com.babyblackdog.ddogdog.order.controller.dto.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record OrderCreateRequest(
    @NotNull(message = "숙소 번호를 넣어주세요.") Long placeId,
    @NotNull(message = "객실 번호를 넣어주세요.") Long roomId,
    @NotNull(message = "유저 아이디를 넣어주세요.") Long userId,
    LocalDate checkIn,
    LocalDate checkOut
) {

}
