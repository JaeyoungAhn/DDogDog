package com.babyblackdog.ddogdog.order.controller.dto.response;

import com.babyblackdog.ddogdog.common.point.Point;
import com.babyblackdog.ddogdog.order.service.dto.result.OrderCancelResult;
import java.time.LocalDate;

public record OrderCancelResponse(Point canceledPoint, LocalDate checkIn, LocalDate checkOut) {

    public static OrderCancelResponse of(OrderCancelResult result) {
        return new OrderCancelResponse(
                result.usedPoint(),
                result.stayPeriod().getCheckIn(),
                result.stayPeriod().getCheckOut()
        );
    }
}
