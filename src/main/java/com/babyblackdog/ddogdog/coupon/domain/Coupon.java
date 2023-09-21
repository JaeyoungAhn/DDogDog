package com.babyblackdog.ddogdog.coupon.domain;

import com.babyblackdog.ddogdog.coupon.domain.vo.CouponType;
import com.babyblackdog.ddogdog.coupon.domain.vo.DiscountType;
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

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    public Coupon(String couponName, DiscountType discountType, String redemptionCode, Long issueCount,
            LocalDate startDate,
            LocalDate endDate) {
        this.couponName = couponName;
        this.discountType = discountType;
        this.redemptionCode = redemptionCode;
        this.issueCount = issueCount;
        this.startDate = startDate;
        this.endDate = endDate;
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
