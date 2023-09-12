package com.babyblackdog.ddogdog.order.service;

import com.babyblackdog.ddogdog.common.date.StayPeriod;
import com.babyblackdog.ddogdog.common.point.Point;
import com.babyblackdog.ddogdog.order.domain.Order;
import com.babyblackdog.ddogdog.order.domain.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;

    public OrderServiceImpl(OrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public Long create(Long userId, StayPeriod stayPeriod, Point pointToPay) {
        Order savedOrder = repository.save(new Order(userId, stayPeriod, pointToPay));
        return savedOrder.getId();
    }

    @Override
    public void complete(Long orderId) {
        Order foundOrder = repository.findById(orderId).orElseThrow(IllegalArgumentException::new);
        foundOrder.complete();
    }
}
