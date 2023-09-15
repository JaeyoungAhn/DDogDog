package com.babyblackdog.ddogdog.order.service.dto.result;

import com.babyblackdog.ddogdog.common.point.Point;
import com.babyblackdog.ddogdog.reservation.service.StayPeriod;

public record OrderCancelResult(Point point, StayPeriod stayPeriod) {

}
