package com.babyblackdog.ddogdog.coupon.domain.vo;

import static com.babyblackdog.ddogdog.global.exception.ErrorCode.INVALID_DISCOUNT_TYPE;

import com.babyblackdog.ddogdog.global.exception.CouponException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public class DiscountValue {

    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type")
    private DiscountType discountType;

    @Column(name = "discount_value")
    private Double value;

    private DiscountValue(DiscountType discountType, Discountable discount) {
        this.discountType = discountType;
        this.value = discount.value();
    }

    protected DiscountValue() {
    }

    public static DiscountValue from(String discountTypeStr, Double discountValue) {
        return switch (discountTypeStr.toLowerCase()) {
            case "amount" -> new DiscountValue(DiscountType.FIXED, new Amount(discountValue));
            case "percent" -> new DiscountValue(DiscountType.PERCENT, new Percent(discountValue));
            default -> throw new CouponException(INVALID_DISCOUNT_TYPE);
        };
    }

    public Double getDiscount(Double originalPrice) {
        return getDiscountable().applyDiscount(originalPrice);
    }

    public Double getValue() {
        return value;
    }

    public DiscountType getType() {
        return discountType;
    }

    private Discountable getDiscountable() {
        return switch (discountType) {
            case FIXED -> new Amount(value);
            case PERCENT -> new Percent(value);
        };
    }

}
