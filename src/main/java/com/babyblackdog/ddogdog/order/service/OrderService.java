package com.babyblackdog.ddogdog.order.service;

import com.babyblackdog.ddogdog.common.point.Point;
import com.babyblackdog.ddogdog.order.service.dto.result.OrderCancelResult;
import com.babyblackdog.ddogdog.order.service.dto.result.OrderInformationResult;
import com.babyblackdog.ddogdog.reservation.service.StayPeriod;

public interface OrderService {

    Long create(StayPeriod stayPeriod, Point pointToPay);

    void complete(Long createdOrderId);

    OrderInformationResult find(long orderId);

    OrderCancelResult cancel(Long orderId);
}
