package com.babyblackdog.ddogdog.coupon.domain;

import com.babyblackdog.ddogdog.common.auth.Email;
import com.babyblackdog.ddogdog.coupon.domain.vo.CouponUsageStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "coupon_usage")
public class CouponUsage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @Embedded
    private Email email;

    @Column(name = "claim_date")
    private LocalDate claimDate;

    @Column(name = "activation_date")
    private LocalDate activationDate;

    @Enumerated(EnumType.STRING)
    private CouponUsageStatus couponUsageStatus;

    public Coupon getCoupon() {
        return coupon;
    }

    public Email getEmail() {
        return email;
    }

    public LocalDate getClaimDate() {
        return claimDate;
    }

    public LocalDate getActivationDate() {
        return activationDate;
    }

    public CouponUsageStatus getCouponUsageStatus() {
        return couponUsageStatus;
    }

    public void setActivationDate(LocalDate activationDate) {
        this.activationDate = activationDate;
    }

    public void setCouponUsageStatus(CouponUsageStatus couponUsageStatus) {
        this.couponUsageStatus = couponUsageStatus;
    }
}
