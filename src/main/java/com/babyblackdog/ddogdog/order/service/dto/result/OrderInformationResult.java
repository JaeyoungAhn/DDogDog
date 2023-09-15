package com.babyblackdog.ddogdog.order.service.dto.result;

import com.babyblackdog.ddogdog.common.point.Point;
import com.babyblackdog.ddogdog.reservation.service.StayPeriod;

public record OrderInformationResult(Point usedPoint, StayPeriod stayPeriod, String orderStatus) {

}
