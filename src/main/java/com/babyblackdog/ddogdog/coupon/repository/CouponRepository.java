package com.babyblackdog.ddogdog.coupon.repository;

import com.babyblackdog.ddogdog.coupon.domain.Coupon;
import jakarta.persistence.LockModeType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    List<Coupon> findByRoomIdInAndRemainingCountIsNull(List<Long> roomIds);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Coupon findCouponByPromoCode(String promoCode);

    @Query("SELECT c.roomId FROM Coupon c WHERE c.id = :couponId")
    Long findRoomIdById(@Param("couponId") Long couponId);
}