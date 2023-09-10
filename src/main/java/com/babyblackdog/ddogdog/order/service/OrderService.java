package com.babyblackdog.ddogdog.order.service;

import java.time.LocalDate;

public interface OrderService {

  Long create(long originPoint, long paymentPoint, LocalDate paymentDate);
}
