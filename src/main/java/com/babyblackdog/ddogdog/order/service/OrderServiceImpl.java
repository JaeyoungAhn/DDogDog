package com.babyblackdog.ddogdog.order.service;

import com.babyblackdog.ddogdog.common.date.StayPeriod;
import com.babyblackdog.ddogdog.order.domain.Order;
import com.babyblackdog.ddogdog.order.domain.OrderRepository;
import java.time.LocalDate;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

  private final OrderRepository repository;

  public OrderServiceImpl(OrderRepository repository) {
    this.repository = repository;
  }

  @Override
  public Long create(Long userId, StayPeriod stayPeriod) {
    Order savedOrder = repository.save(new Order(userId, stayPeriod));
    return savedOrder.getId();
  }
}
