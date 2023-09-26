package com.babyblackdog.ddogdog.coupon.service.dto.result;

import com.babyblackdog.ddogdog.coupon.domain.Coupon;
import com.babyblackdog.ddogdog.coupon.domain.vo.CouponName;
import com.babyblackdog.ddogdog.coupon.domain.vo.CouponType;
import com.babyblackdog.ddogdog.coupon.domain.vo.DiscountType;
import java.time.LocalDate;

public record ManualCouponCreationResult(Long couponId, CouponName couponName, CouponType couponType,
                                         DiscountType discountType,
                                         Double discountValue,
                                         String promoCode, Long issueCount, Long remainingCount, LocalDate startDate,
                                         LocalDate endDate) {

    public static ManualCouponCreationResult of(Coupon coupon) {
        return new ManualCouponCreationResult(
                coupon.getId(),
                coupon.getCouponName(),
                coupon.getCouponType(),
                coupon.getDiscountValue().getType(),
                coupon.getDiscountValue().getValue(),
                coupon.getPromoCode(),
                coupon.getIssueCount(),
                coupon.getRemainingCount(),
                coupon.getStartDate(),
                coupon.getEndDate()
        );
    }
}
