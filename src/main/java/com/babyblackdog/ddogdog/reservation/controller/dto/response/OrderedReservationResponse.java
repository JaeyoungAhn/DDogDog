package com.babyblackdog.ddogdog.reservation.controller.dto.response;

import com.babyblackdog.ddogdog.reservation.service.dto.result.OrderedReservationResult;

public record OrderedReservationResponse(Long reservationId) {

    public static OrderedReservationResponse of(OrderedReservationResult result) {
        return new OrderedReservationResponse(result.reservationId());
    }
}
