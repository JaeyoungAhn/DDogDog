package com.babyblackdog.ddogdog.coupon.application;

import com.babyblackdog.ddogdog.common.auth.Email;
import com.babyblackdog.ddogdog.coupon.service.dto.InstantCouponCreationResult;
import com.babyblackdog.ddogdog.coupon.service.dto.InstantCouponResults;
import com.babyblackdog.ddogdog.coupon.service.dto.InstantCouponUsageResult;
import com.babyblackdog.ddogdog.coupon.service.dto.ManualCouponClaimResult;
import com.babyblackdog.ddogdog.coupon.service.dto.ManualCouponCreationResult;
import com.babyblackdog.ddogdog.coupon.service.dto.ManualCouponResults;
import com.babyblackdog.ddogdog.coupon.service.dto.ManualCouponUsageResult;
import java.time.LocalDate;
import org.springframework.stereotype.Service;

@Service
public class CouponFacade {

    public ManualCouponCreationResult registerManualCoupon(Email email, String couponName, String discountType,
            Double discountValue,
            String promoCode, Long issueCount, LocalDate startDate,
            LocalDate endDate) {

        // todo: 똑독의 관리자가 아니면 접근 금지

        return null;
    }

    public InstantCouponCreationResult registerInstantCoupon(Email email, Long roomId, String couponName,
            String discountType, Double discountValue,
            LocalDate startDate, LocalDate endDate) {

        // todo: roomId의 주인이 아니면 접근 금지

        return null;
    }

    public ManualCouponResults findAvailableManualCouponsByEmail(Email email) {

        return null;
    }

    public InstantCouponResults findAvailableInstantCouponsByHotelId(Long hotelId) {

        return null;
    }

    public ManualCouponUsageResult useManualCoupon(Long couponUsageId) {

        // todo: couponUsageId의 이메일과 일치하지 않으면 접근 금지

        return null;
    }

    public InstantCouponUsageResult useInstantCoupon() {

        return null;
    }

    public void deleteInstantCoupon(Email email, Long couponId) {

        // todo: couponId의 roomId 주인이 아니면 접근 금지

        return;
    }

    public ManualCouponClaimResult claimManualCoupon(Email email, String promoCode) {

        return null;
    }
}
