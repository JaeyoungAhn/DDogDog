package com.babyblackdog.ddogdog.order.service.dto.result;

import com.babyblackdog.ddogdog.common.date.StayPeriod;
import com.babyblackdog.ddogdog.common.point.Point;

public record OrderCancelResult(Point point, StayPeriod stayPeriod) {

}
