package com.babyblackdog.ddogdog.coupon.application;

import com.babyblackdog.ddogdog.common.auth.Email;
import com.babyblackdog.ddogdog.coupon.service.dto.InstantCouponCreationResult;
import com.babyblackdog.ddogdog.coupon.service.dto.InstantCouponFindResults;
import com.babyblackdog.ddogdog.coupon.service.dto.InstantCouponUsageResult;
import com.babyblackdog.ddogdog.coupon.service.dto.ManualCouponClaimResult;
import com.babyblackdog.ddogdog.coupon.service.dto.ManualCouponCreationResult;
import com.babyblackdog.ddogdog.coupon.service.dto.ManualCouponFindResults;
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

        // todo: 유저 서비스에 이메일을 전달하고 관리자인지 문의 => 유저 도메인

        return null;
    }

    public InstantCouponCreationResult registerInstantCoupon(Email email, Long roomId, String couponName,
            String discountType, Double discountValue,
            LocalDate startDate, LocalDate endDate) {

        // todo: roomId의 주인이 아니면 접근 금지

        // todo: email과 roomId를 place도메인에 전달해서 email의 소유주와 숙소들 중 roomId가 존재하는지 확인

        return null;
    }

    public ManualCouponFindResults findAvailableManualCouponsByEmail(Email email) {

        return null;
    }

    public InstantCouponFindResults findAvailableInstantCouponsByHotelId(Long hotelId) {

        // todo: Place에 HotelId를 주고 해당하는 List<roomId>를 받아옴

        // todo: roomIds를 이용해서

        return null;
    }

    public ManualCouponClaimResult claimManualCoupon(Email email, String promoCode) {

        // todo: promoCode를 이용해서 couponId 받아오기 => 서비스1

        // todo: couponId와 email을 이용해 couponUsage 만들기 => 서비스2

        // todo : 동시성 문제 1개를 빼고 나머지는 순차적으로 받아오면 되지만 마지막 1개의 경우는?

        return null;
    }

    public ManualCouponUsageResult useManualCoupon(Email email, Long couponUsageId) {

        // todo: couponUsageId의 이메일과 일치하지 않으면 접근 금지

        // todo : 실제 가격 변동 처리..

        // todo : order에 couponUsageId가 notNull인 경우, 쿠폰 측에 할인후 가격을 요청
        // todo : coupon

        return null;
    }

    public InstantCouponUsageResult useInstantCoupon(Email email, Long couponId) {

        // todo : email과 couponId에 저장된 이메일이 같으면 상태 변경

        // todo : 실제 가격 변동 처리

        // todo : order에 couponUsageId가 notNull인 경우, 쿠폰 측에 할인 후 가격을 요청

        return null;
    }

    public void deleteInstantCoupon(Email email, Long couponId) {

        // todo: couponId의 roomId 주인이 아니면 접근 금지

        // todo: couponId를 통해 roomId를 받아옴 => 쿠폰 도메인

        // todo: email과 roomId를 place도메인에 전달해서 email의 소유주와 숙소들 중 roomId가 존재하는지 확인

        return;
    }
}
