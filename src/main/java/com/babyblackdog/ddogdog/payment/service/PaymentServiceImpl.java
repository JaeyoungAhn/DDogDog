package com.babyblackdog.ddogdog.payment.service;

import java.time.LocalDate;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService{

    @Override
    public Long create(long originPoint, long paymentPoint, LocalDate paymentDate) {
        return null;
    }
}
