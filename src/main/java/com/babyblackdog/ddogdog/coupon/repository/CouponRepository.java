package com.babyblackdog.ddogdog.coupon.repository;

import com.babyblackdog.ddogdog.coupon.domain.Coupon;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    List<Coupon> findCouponsByRoomIdIn(List<Long> roomIds);

    Coupon findCouponByPromoCode(String promoCode);

    Long findRoomIdById(Long couponId);
}