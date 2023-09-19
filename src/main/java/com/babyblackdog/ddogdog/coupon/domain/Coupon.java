package com.babyblackdog.ddogdog.coupon.domain;

import com.babyblackdog.ddogdog.coupon.domain.vo.CouponType;
import com.babyblackdog.ddogdog.coupon.domain.vo.DiscountType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "coupons")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "coupon_name")
    private String couponName;

    @Embedded
    private CouponType couponType;

    @Embedded
    private DiscountType discountType;

    @Column(name = "redemption_code")
    private String redemptionCode;

    @Column(name = "issue_count")
    private Long issueCount;

    @Column(name = "remaining_count")
    private Long remainingCount;

    @Column(name = "expire_date")
    private LocalDate expireDate;

    public Coupon(String couponName, CouponType couponType, DiscountType discountType, String redemptionCode,
            Long issueCount, Long remainingCount, LocalDate expireDate) {
        this.couponName = couponName;
        this.couponType = couponType;
        this.discountType = discountType;
        this.redemptionCode = redemptionCode;
        this.issueCount = issueCount;
        this.remainingCount = remainingCount;
        this.expireDate = expireDate;
    }

    protected Coupon() {
    }

    public Long getId() {
        return id;
    }

    public String getCouponName() {
        return couponName;
    }

    public CouponType getCouponType() {
        return couponType;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public String getRedemptionCode() {
        return redemptionCode;
    }

    public Long getIssueCount() {
        return issueCount;
    }

    public Long getRemainingCount() {
        return remainingCount;
    }

    public LocalDate getExpireDate() {
        return expireDate;
    }


}
