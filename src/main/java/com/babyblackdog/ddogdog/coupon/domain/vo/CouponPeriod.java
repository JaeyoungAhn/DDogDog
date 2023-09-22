package com.babyblackdog.ddogdog.coupon.domain.vo;

import static com.babyblackdog.ddogdog.global.exception.ErrorCode.INVALID_COUPON_PERIOD;

import com.babyblackdog.ddogdog.global.exception.CouponException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDate;

@Embeddable
public class CouponPeriod {

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    public CouponPeriod(LocalDate startDate, LocalDate endDate) {
        assertIsEndDateAfterOrSameAsStartDate(startDate, endDate);
        assertIsDifferenceLessThanOneMonth(startDate, endDate);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    protected CouponPeriod() {
    }

    private void assertIsEndDateAfterOrSameAsStartDate(LocalDate startDate, LocalDate endDate) {
        if (endDate.isBefore(startDate)) {
            throw new CouponException(INVALID_COUPON_PERIOD);
        }

    }

    private void assertIsDifferenceLessThanOneMonth(LocalDate startDate, LocalDate endDate) {
        if (endDate.isAfter(startDate.plusMonths(1))) {
            throw new CouponException(INVALID_COUPON_PERIOD);
        }
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
