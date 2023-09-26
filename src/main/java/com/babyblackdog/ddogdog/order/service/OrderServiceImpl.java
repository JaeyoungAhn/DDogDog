package com.babyblackdog.ddogdog.order.service;

import com.babyblackdog.ddogdog.common.auth.Email;
import com.babyblackdog.ddogdog.common.auth.JwtSimpleAuthentication;
import com.babyblackdog.ddogdog.common.point.Point;
import com.babyblackdog.ddogdog.global.exception.ErrorCode;
import com.babyblackdog.ddogdog.global.exception.OrderException;
import com.babyblackdog.ddogdog.order.domain.Order;
import com.babyblackdog.ddogdog.order.domain.OrderRepository;
import com.babyblackdog.ddogdog.order.service.dto.result.OrderCancelResult;
import com.babyblackdog.ddogdog.order.service.dto.result.OrderInformationResult;
import com.babyblackdog.ddogdog.reservation.service.StayPeriod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;
    private final JwtSimpleAuthentication authentication;

    public OrderServiceImpl(OrderRepository repository, JwtSimpleAuthentication authentication) {
        this.repository = repository;
        this.authentication = authentication;
    }

    @Override
    @Transactional
    public Long create(StayPeriod stayPeriod, Point pointToPay) {
        Order savedOrder = repository.save(new Order(getCurrentUserEmail(), stayPeriod, pointToPay));
        return savedOrder.getId();
    }

    @Override
    @Transactional
    public void complete(Long orderId) {
        Order foundOrder = findOrderById(orderId);
        foundOrder.complete();
    }

    @Override
    public OrderInformationResult find(long orderId) {
        Order foundOrder = findOrderById(orderId);
        validateOrderOwner(foundOrder);

        return new OrderInformationResult(
                foundOrder.getUsedPoint(),
                foundOrder.getStayPeriod(),
                foundOrder.getOrderStatus().getDescription()
        );
    }

    @Override
    @Transactional
    public OrderCancelResult cancel(Long orderId) {
        Order foundOrder = findOrderById(orderId);

        foundOrder.cancel(getCurrentUserEmail());

        return new OrderCancelResult(
                foundOrder.getUsedPoint(),
                foundOrder.getStayPeriod()
        );
    }

    private Email getCurrentUserEmail() {
        return authentication.getEmail();
    }

    private Order findOrderById(Long orderId) {
        return repository.findById(orderId).orElseThrow(
                () -> new OrderException(ErrorCode.ORDER_NOT_FOUND)
        );
    }

    private void validateOrderOwner(Order foundOrder) {
        if (!foundOrder.isOrderAuthorValid(getCurrentUserEmail())) {
            throw new OrderException(ErrorCode.UNAUTHORIZED_TO_VIEW_ORDER);
        }
    }
}
