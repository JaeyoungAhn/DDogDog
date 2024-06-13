package com.babyblackdog.ddogdog.coupon.repository;

import com.babyblackdog.ddogdog.coupon.domain.Coupon;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    List<Coupon> findByRoomIdInAndRemainingCountIsNull(List<Long> roomIds);

    Coupon findCouponByPromoCode(String promoCode);

    @Query("SELECT c.roomId FROM Coupon c WHERE c.id = :couponId")
    Long findRoomIdById(@Param("couponId") Long couponId);

    @Modifying
    @Query(value = "UPDATE coupons SET remaining_count = remaining_count - 1 " +
                        "WHERE promo_code = :promoCode AND remaining_count > 0", nativeQuery = true)
    int decrementCouponCount(@Param("promoCode") String promoCode);
}