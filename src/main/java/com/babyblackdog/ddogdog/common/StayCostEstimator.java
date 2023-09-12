package com.babyblackdog.ddogdog.common;

import com.babyblackdog.ddogdog.common.date.StayPeriod;
import com.babyblackdog.ddogdog.common.point.Point;
import java.time.temporal.ChronoUnit;
import org.springframework.stereotype.Component;

@Component
public class StayCostEstimator {

    public Point calculateTotalCost(StayPeriod stayPeriod, Point dailyCost) {
        long stayDays = ChronoUnit.DAYS.between(stayPeriod.checkOut(), stayPeriod.checkIn());
        return new Point(stayDays * dailyCost.getValue());
    }
}
