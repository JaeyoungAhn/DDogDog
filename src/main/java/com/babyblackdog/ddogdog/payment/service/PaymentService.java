package com.babyblackdog.ddogdog.payment.service;

import java.time.LocalDate;

public interface PaymentService {

  Long create(long originPoint, long paymentPoint, LocalDate paymentDate);
}
