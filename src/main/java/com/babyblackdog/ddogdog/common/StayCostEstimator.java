package com.babyblackdog.ddogdog.common;

import com.babyblackdog.ddogdog.common.date.StayPeriod;
import com.babyblackdog.ddogdog.common.point.Point;
import org.springframework.stereotype.Component;

@Component
public class StayCostEstimator {

    public Point calculateTotalCost(StayPeriod stayPeriod, Point dailyCost) {
        return new Point(stayPeriod.getPeriod() * dailyCost.getValue());
    }
}
