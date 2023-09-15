package com.babyblackdog.ddogdog.order.service;

import com.babyblackdog.ddogdog.common.date.StayPeriod;
import com.babyblackdog.ddogdog.common.point.Point;
import com.babyblackdog.ddogdog.order.domain.Order;
import com.babyblackdog.ddogdog.order.domain.OrderRepository;
import com.babyblackdog.ddogdog.order.service.dto.result.OrderCancelResult;
import com.babyblackdog.ddogdog.order.service.dto.result.OrderInformationResult;
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

    @Override
    public OrderInformationResult find(long orderId, long userId) {
        Order foundOrder = repository.findById(orderId).orElseThrow(IllegalArgumentException::new);
        if (foundOrder.isOrderAuthorValid(userId)) {
            throw new IllegalStateException("주문을 한 유저만 내역을 확인할 수 있습니다.");
        }

        return new OrderInformationResult(
                foundOrder.getUsedPoint(),
                foundOrder.getStayPeriod(),
                foundOrder.getOrderStatus().toString()
        );
    }

    @Override
    public OrderCancelResult cancel(Long orderId, long userId) {
        Order foundOrder = repository.findById(orderId).orElseThrow(IllegalArgumentException::new);

        foundOrder.cancel(userId);

        return new OrderCancelResult(
                foundOrder.getUsedPoint(),
                foundOrder.getStayPeriod()
        );
    }
}
