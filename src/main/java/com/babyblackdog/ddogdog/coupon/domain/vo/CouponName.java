package com.babyblackdog.ddogdog.coupon.domain.vo;

import static com.babyblackdog.ddogdog.global.exception.ErrorCode.INVALID_COUPON_NAME;

import com.babyblackdog.ddogdog.global.exception.CouponException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class CouponName {

    @Column(name = "coupon_name")
    private String value;

    public CouponName(String value) {
        validateNameLength(value);
        this.value = value;
    }

    protected CouponName() {
    }

    private void validateNameLength(String couponName) {
        if (couponName.length() >= 50) {
            throw new CouponException(INVALID_COUPON_NAME);
        }
    }

    public String getValue() {
        return value;
    }
}
