package com.babyblackdog.ddogdog.coupon.repository;

import com.babyblackdog.ddogdog.common.auth.Email;
import com.babyblackdog.ddogdog.coupon.domain.CouponUsage;
import com.babyblackdog.ddogdog.coupon.domain.vo.CouponUsageStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponUsageRepository extends JpaRepository<CouponUsage, Long> {

    List<CouponUsage> findCouponUsagesByEmailAndCouponUsageStatus(Email email, CouponUsageStatus couponUsageStatus);

    @Query("SELECT cu FROM CouponUsage cu JOIN FETCH cu.coupon WHERE cu.id = :id")
    Optional<CouponUsage> findWithCouponById(@Param("id") Long couponUsageId);

    boolean existsByEmailAndCouponId(Email email, Long couponId);
}