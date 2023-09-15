package com.babyblackdog.ddogdog.order.service;

import com.babyblackdog.ddogdog.common.auth.Email;
import com.babyblackdog.ddogdog.common.auth.JwtSimpleAuthentication;
import com.babyblackdog.ddogdog.common.point.Point;
import com.babyblackdog.ddogdog.order.domain.Order;
import com.babyblackdog.ddogdog.order.domain.OrderRepository;
import com.babyblackdog.ddogdog.order.service.dto.result.OrderCancelResult;
import com.babyblackdog.ddogdog.order.service.dto.result.OrderInformationResult;
import com.babyblackdog.ddogdog.reservation.service.StayPeriod;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;

    public OrderServiceImpl(OrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public Long create(StayPeriod stayPeriod, Point pointToPay) {
        Email userEmail = JwtSimpleAuthentication.getInstance().getEmail();

        Order savedOrder = repository.save(new Order(userEmail, stayPeriod, pointToPay));
        return savedOrder.getId();
    }

    @Override
    public void complete(Long orderId) {
        Order foundOrder = repository.findById(orderId).orElseThrow(IllegalArgumentException::new);
        foundOrder.complete();
    }

    @Override
    public OrderInformationResult find(long orderId) {
        Email userEmail = JwtSimpleAuthentication.getInstance().getEmail();

        Order foundOrder = repository.findById(orderId).orElseThrow(IllegalArgumentException::new);
        if (foundOrder.isOrderAuthorValid(userEmail)) {
            throw new IllegalStateException("주문을 한 유저만 내역을 확인할 수 있습니다.");
        }

        return new OrderInformationResult(
                foundOrder.getUsedPoint(),
                foundOrder.getStayPeriod(),
                foundOrder.getOrderStatus().toString()
        );
    }

    @Override
    public OrderCancelResult cancel(Long orderId) {
        Email userEmail = JwtSimpleAuthentication.getInstance().getEmail();

        Order foundOrder = repository.findById(orderId).orElseThrow(IllegalArgumentException::new);

        foundOrder.cancel(userEmail);

        return new OrderCancelResult(
                foundOrder.getUsedPoint(),
                foundOrder.getStayPeriod()
        );
    }
}
