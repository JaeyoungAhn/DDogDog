package com.babyblackdog.ddogdog.order.service;

import org.springframework.stereotype.Service;

@Service
public class OrderReaderServiceImpl implements OrderReaderService {

    @Override
    public boolean isStayOver(Long orderId) {
        return false;
    }
}
