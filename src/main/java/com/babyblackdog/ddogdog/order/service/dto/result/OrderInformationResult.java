package com.babyblackdog.ddogdog.order.service.dto.result;

import com.babyblackdog.ddogdog.common.date.StayPeriod;
import com.babyblackdog.ddogdog.common.point.Point;

public record OrderInformationResult(Point usedPoint, StayPeriod stayPeriod, String orderStatus) {

}
