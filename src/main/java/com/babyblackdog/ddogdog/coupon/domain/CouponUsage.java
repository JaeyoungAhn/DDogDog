package com.babyblackdog.ddogdog.coupon.domain;

import com.babyblackdog.ddogdog.common.auth.Email;
import com.babyblackdog.ddogdog.coupon.domain.vo.CouponUsageStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
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
    @CreatedDate
    private LocalDate claimDate;

    @Column(name = "activation_date")
    private LocalDate activationDate;

    @Enumerated(EnumType.STRING)
    private CouponUsageStatus couponUsageStatus;

    public CouponUsage(Email email, Coupon coupon) {
        this.email = email;
        this.coupon = coupon;
        this.couponUsageStatus = CouponUsageStatus.CLAIMED;
    }

    protected CouponUsage() {
    }

    public Long getId() {
        return id;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public Email getEmail() {
        return email;
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
