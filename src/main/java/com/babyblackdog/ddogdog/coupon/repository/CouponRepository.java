package com.babyblackdog.ddogdog.coupon.repository;

import com.babyblackdog.ddogdog.coupon.domain.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

}