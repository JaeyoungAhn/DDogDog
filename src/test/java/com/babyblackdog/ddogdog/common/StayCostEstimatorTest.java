package com.babyblackdog.ddogdog.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import com.babyblackdog.ddogdog.common.date.StayPeriod;
import com.babyblackdog.ddogdog.common.point.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class StayCostEstimatorTest {

    private StayCostEstimator stayCostEstimator;

    @BeforeEach
    public void setup() {
        stayCostEstimator = new StayCostEstimator();
    }


    @Test
    @DisplayName("머무는 기간의 가격을 반환한다.")
    public void calculateTotalCost_success() {
        // Given
        StayPeriod mockStayPeriod = Mockito.mock(StayPeriod.class);
        given(mockStayPeriod.getPeriod()).willReturn(5L);

        Point dailyCost = new Point(100);

        // When
        Point totalCost = stayCostEstimator.calculateTotalCost(mockStayPeriod, dailyCost);

        // Then
        assertEquals(500, totalCost.getValue());
    }
}
