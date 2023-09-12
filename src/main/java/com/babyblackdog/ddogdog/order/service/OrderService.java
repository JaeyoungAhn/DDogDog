package com.babyblackdog.ddogdog.order.service;

import com.babyblackdog.ddogdog.common.date.StayPeriod;
import com.babyblackdog.ddogdog.common.point.Point;
import java.time.LocalDate;

public interface OrderService {

  Long create(Long userId, StayPeriod stayPeriod, Point pointToPay);

  void complete(Long createdOrderId);
}
