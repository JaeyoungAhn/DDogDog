package com.babyblackdog.ddogdog.order.controller.dto.response;

import com.babyblackdog.ddogdog.order.service.dto.result.OrderCreateResult;

public record OrderCreateResponse(Long orderId) {

    public static OrderCreateResponse of(OrderCreateResult result) {
        return new OrderCreateResponse(result.orderId());
    }
}
