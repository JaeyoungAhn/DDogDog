package com.babyblackdog.ddogdog.coupon.domain.vo;

import com.babyblackdog.ddogdog.common.point.Point;

public interface Discountable {

    Double value();

    Point getDiscountAmount(Point originalPrice);
}
