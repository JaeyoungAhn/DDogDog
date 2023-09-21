package com.babyblackdog.ddogdog.coupon.domain.vo;

import static com.babyblackdog.ddogdog.global.exception.ErrorCode.INVALID_DISCOUNT_VALUE;

import com.babyblackdog.ddogdog.global.exception.CouponException;

public record Percent(Double value) implements Discountable {

    @Override
    public Double applyDiscount(Double originalPrice) {
        return originalPrice * (1 - (value / 100));
    }

    public Percent {
        assertValidPercentage(value);
    }

    private void assertValidPercentage(Double value) {
        if (value < 1 || value > 100) {
            throw new CouponException(INVALID_DISCOUNT_VALUE);
        }
    }
}
