package com.babyblackdog.ddogdog.order.service;

import com.babyblackdog.ddogdog.common.date.StayPeriod;
import java.time.LocalDate;

public interface OrderService {

  Long create(Long userId, StayPeriod stayPeriod);
}
