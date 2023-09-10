package com.babyblackdog.ddogdog.order.service;

import java.time.LocalDate;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

  @Override
  public Long create(long originPoint, long paymentPoint, LocalDate paymentDate) {
    return null;
  }
}
