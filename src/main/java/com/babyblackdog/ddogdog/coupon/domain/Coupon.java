package com.babyblackdog.ddogdog.coupon.domain;

import com.babyblackdog.ddogdog.coupon.domain.vo.CouponName;
import com.babyblackdog.ddogdog.coupon.domain.vo.CouponPeriod;
import com.babyblackdog.ddogdog.coupon.domain.vo.CouponType;
import com.babyblackdog.ddogdog.coupon.domain.vo.DiscountValue;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    @Column(name = "room_id")
    private Long roomId;

    @Embedded
    private CouponName couponName;

    @Column(name = "coupon_type")
    @Enumerated(EnumType.STRING)
    private CouponType couponType;

    @Embedded
    private DiscountValue discountValue;

    @Column(name = "promo_code")
    private String promoCode;

    @Column(name = "issue_count")
    private Long issueCount;

    @Column(name = "remaining_count")
    private Long remainingCount;

    @Embedded
    private CouponPeriod couponPeriod;

    public Coupon(
            CouponName couponName,
            DiscountValue discountValue,
            String promoCode,
            Long issueCount,
            CouponPeriod couponPeriod
    ) {
        this.couponName = couponName;
        this.couponType = CouponType.MANUAL;
        this.discountValue = discountValue;
        this.promoCode = promoCode;
        this.issueCount = issueCount;
        this.couponPeriod = couponPeriod;
        this.remainingCount = issueCount;
    }

    public Coupon(
            Long roomId,
            CouponName couponName,
            DiscountValue discountValue,
            CouponPeriod couponPeriod
    ) {
        this.roomId = roomId;
        this.couponType = CouponType.INSTANT;
        this.couponName = couponName;
        this.discountValue = discountValue;
        this.couponPeriod = couponPeriod;
    }

    protected Coupon() {
    }

    public Long getId() {
        return id;
    }

    public Long getRoomId() {
        return roomId;
    }

    public CouponName getCouponName() {
        return couponName;
    }

    public CouponType getCouponType() {
        return couponType;
    }

    public DiscountValue getDiscountValue() {
        return discountValue;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public Long getIssueCount() {
        return issueCount;
    }

    public Long getRemainingCount() {
        return remainingCount;
    }

    public void setCouponType(CouponType couponType) {
        this.couponType = couponType;
    }

    public void setRemainingCount(Long remainingCount) {
        this.remainingCount = remainingCount;
    }

    public LocalDate getStartDate() {
        return couponPeriod.getStartDate();
    }

    public LocalDate getEndDate() {
        return couponPeriod.getEndDate();
    }
}
