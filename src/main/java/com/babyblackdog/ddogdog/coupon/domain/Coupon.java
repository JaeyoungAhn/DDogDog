package com.babyblackdog.ddogdog.coupon.domain;

import com.babyblackdog.ddogdog.coupon.domain.vo.CouponName;
import com.babyblackdog.ddogdog.coupon.domain.vo.CouponType;
import com.babyblackdog.ddogdog.coupon.domain.vo.DiscountValue;
import com.babyblackdog.ddogdog.place.model.Room;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "coupons")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Embedded
    private CouponName couponName;

    @Embedded
    private CouponType couponType;

    @Embedded
    private DiscountValue discountValue;

    @Column(name = "promo_code")
    private String promoCode;

    @Column(name = "issue_count")
    private Long issueCount;

    @Column(name = "remaining_count")
    private Long remainingCount;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    public Coupon(CouponName couponName, DiscountValue discountValue, String promoCode,
            Long issueCount,
            LocalDate startDate,
            LocalDate endDate) {
        this.couponName = couponName;
        this.discountValue = discountValue;
        this.promoCode = promoCode;
        this.issueCount = issueCount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    protected Coupon() {
    }

    public Long getId() {
        return id;
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setCouponType(CouponType couponType) {
        this.couponType = couponType;
    }

    public void setRemainingCount(Long remainingCount) {
        this.remainingCount = remainingCount;
    }
}
