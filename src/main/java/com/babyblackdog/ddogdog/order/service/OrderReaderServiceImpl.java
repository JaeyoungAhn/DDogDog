package com.babyblackdog.ddogdog.order.service;

import com.babyblackdog.ddogdog.common.auth.JwtSimpleAuthentication;
import com.babyblackdog.ddogdog.order.domain.Order;
import com.babyblackdog.ddogdog.order.domain.OrderRepository;
import com.babyblackdog.ddogdog.order.domain.OrderStatus;
import com.babyblackdog.ddogdog.reservation.service.TimeProvider;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class OrderReaderServiceImpl implements OrderReaderService {

    JwtSimpleAuthentication authentication;
    OrderRepository orderRepository;

    TimeProvider timeProvider;

    public OrderReaderServiceImpl(JwtSimpleAuthentication authentication, OrderRepository orderRepository,
            TimeProvider timeProvider) {
        this.authentication = authentication;
        this.orderRepository = orderRepository;
        this.timeProvider = timeProvider;
    }

    @Override
    public boolean isStayOver(Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty()) {
            return false;
        }

        Order order = optionalOrder.get();
        return isOrderValidForStayOver(order);
    }

    private boolean isOrderValidForStayOver(Order order) {
        return isAuthorValid(order) && isStatusCompleted(order) && isCheckOutDatePast(order);
    }

    private boolean isAuthorValid(Order order) {
        return order.isOrderAuthorValid(authentication.getEmail());
    }

    private boolean isStatusCompleted(Order order) {
        return order.getOrderStatus() == OrderStatus.COMPLETED;
    }

    private boolean isCheckOutDatePast(Order order) {
        return !timeProvider.getCurrentDate().isBefore(order.getStayPeriod().getCheckOut());
    }
}
