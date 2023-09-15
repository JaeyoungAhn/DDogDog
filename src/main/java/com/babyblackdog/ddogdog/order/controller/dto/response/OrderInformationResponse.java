package com.babyblackdog.ddogdog.order.controller.dto.response;

import com.babyblackdog.ddogdog.common.point.Point;
import com.babyblackdog.ddogdog.order.service.dto.result.OrderInformationResult;
import java.time.LocalDate;

public record OrderInformationResponse(Point usedPoint,
                                       LocalDate checkIn,
                                       LocalDate checkOut,
                                       String orderStatus) {

    public static OrderInformationResponse of(OrderInformationResult result) {
        return new OrderInformationResponse(
                result.usedPoint(),
                result.stayPeriod().getCheckIn(),
                result.stayPeriod().getCheckOut(),
                result.orderStatus()
        );
    }
}
