package com.babyblackdog.ddogdog.coupon.domain.vo;

public interface Discountable {

    Double value();

    Double applyDiscount(Double originalPrice);
}
