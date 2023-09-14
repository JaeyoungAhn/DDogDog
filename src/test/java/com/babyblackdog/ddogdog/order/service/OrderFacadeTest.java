package com.babyblackdog.ddogdog.order.service;

import com.babyblackdog.ddogdog.place.facade.PlaceFacadeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrderFacadeTest {

    @Autowired
    private OrderFacade orderFacade;

    private PlaceFacadeService facadeService;

    @BeforeEach
    void setup() {
    }

    @Nested
    class FindRoomSummaryMethod {

        @Test
        @DisplayName("방을 찾으면 방의 정보를 반환한다.")
        void success() {

        }

        @Test
        @DisplayName("방이 존재하지 않는다면 예외를 발생시킨다")
        void failureForMissingRoomId() {

        }
    }

    @Nested
    class OrderMethod {

        @Test
        @DisplayName("주문을 성공하면 유저에게 돈을 받고 주문을 완료한다.")
        void success() {
        }

        @Test
        @DisplayName("방의 정보가 없다면 예외를 발생시킨다.")
        void failureForMissingRoomInfo() {
        }

        @Disabled
        @Test
        @DisplayName("유저의 돈이 없다면 예외를 발생시킨다.")
        void failureForMissingUserPoint() {
            // todo: 포인트의 결제를 어디에 두고 어떻게 진행할 지 정해지지 않음
        }
    }

    @Nested
    class CancelOrderMethod {

        @Test
        @DisplayName("주문 완료 상태에서 주문을 취소할 수 있다.")
        void success() {
        }

        @Test
        @DisplayName("주문 완료 상태가 아니라면 주문을 취소할 수 없다.")
        void failureIfNotCompletedStatus() {

        }
    }
}
