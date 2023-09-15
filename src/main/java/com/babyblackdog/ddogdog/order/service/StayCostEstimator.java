package com.babyblackdog.ddogdog.order.service;

import com.babyblackdog.ddogdog.common.point.Point;
import com.babyblackdog.ddogdog.reservation.service.StayPeriod;
import org.springframework.stereotype.Component;

@Component
public class StayCostEstimator {

    public Point calculateTotalCost(StayPeriod stayPeriod, Point dailyCost) {
        return new Point(stayPeriod.getPeriod() * dailyCost.getValue());
    }

}
