package com.babyblackdog.ddogdog.order.service;

import com.babyblackdog.ddogdog.common.date.StayPeriod;
import com.babyblackdog.ddogdog.common.point.Point;
import com.babyblackdog.ddogdog.order.service.dto.result.OrderCancelResult;
import com.babyblackdog.ddogdog.order.service.dto.result.OrderInformationResult;

public interface OrderService {

    Long create(Long userId, StayPeriod stayPeriod, Point pointToPay);

    void complete(Long createdOrderId);

    OrderInformationResult find(long orderId, long userId);

    OrderCancelResult cancel(Long orderId, long userId);
}
