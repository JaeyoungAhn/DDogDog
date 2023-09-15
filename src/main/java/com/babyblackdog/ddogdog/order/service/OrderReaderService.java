package com.babyblackdog.ddogdog.order.service;

public interface OrderReaderService {

    //todo - [리뷰] 예약한 것만 리뷰 등록 가능
    boolean isStayOver(Long orderId);

}
