package com.babyblackdog.ddogdog.order.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;

import com.babyblackdog.ddogdog.common.date.StayPeriod;
import com.babyblackdog.ddogdog.common.date.TimeProvider;
import com.babyblackdog.ddogdog.common.point.Point;
import com.babyblackdog.ddogdog.order.service.dto.result.OrderCancelResult;
import com.babyblackdog.ddogdog.order.service.dto.result.OrderCreateResult;
import com.babyblackdog.ddogdog.order.service.dto.result.RoomOrderPageResult;
import com.babyblackdog.ddogdog.place.reader.PlaceReaderService;
import com.babyblackdog.ddogdog.place.reader.vo.RoomSimpleResult;
import com.babyblackdog.ddogdog.reservation.service.ReservationService;
import com.babyblackdog.ddogdog.user.service.UserService;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class OrderFacadeTest {

    @Autowired
    private OrderFacade orderFacade;

    @MockBean
    private OrderService service;

    @MockBean
    private PlaceReaderService placeReaderService;

    @MockBean
    private ReservationService reservationService;

    @MockBean
    private UserService userService;

    @Autowired
    private TimeProvider timeProvider;

    @Nested
    class FindRoomSummaryMethod {

        @Test
        @DisplayName("방을 찾으면 방의 정보를 반환한다.")
        void success() {
            // Given
            long existentRoomId = 1;
            long period = 5;
            LocalDate checkIn = timeProvider.getCurrentDate();
            LocalDate checkOut = timeProvider.getCurrentDate().plusDays(period);
            StayPeriod stayPeriod = new StayPeriod(checkIn, checkOut, timeProvider);

            String hotelName = "호텔 이름";
            String roomType = "방 타입";
            String roomDescription = "방 설명";
            String roomNumber = "방 번호";
            Point dailyPoint = new Point(100);
            given(placeReaderService.findRoomSimpleInfo(existentRoomId))
                    .willReturn(new RoomSimpleResult(
                            hotelName, roomType, roomDescription, roomNumber, dailyPoint.getValue()
                    ));

            RoomOrderPageResult expected = new RoomOrderPageResult(hotelName,
                    roomType, roomDescription, roomNumber,
                    new Point(dailyPoint.getValue() * period), checkIn, checkOut);

            // When
            RoomOrderPageResult actual = orderFacade.findRoomSummary(existentRoomId,
                    stayPeriod);

            // Then
            assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
        }

        @Test
        @DisplayName("방이 존재하지 않는다면 예외를 발생시킨다")
        void failureForMissingRoomId() {
            // Given
            long nonExistentRoomId = 1;
            StayPeriod stayPeriod = new StayPeriod(
                    timeProvider.getCurrentDate(),
                    timeProvider.getCurrentDate().plusDays(1),
                    timeProvider
            );

            given(placeReaderService.findRoomSimpleInfo(nonExistentRoomId)).willThrow(
                    RuntimeException.class);

            // When & Then
            assertThatThrownBy(() -> orderFacade.findRoomSummary(nonExistentRoomId, stayPeriod))
                    .isInstanceOf(RuntimeException.class);
        }
    }

    @Nested
    class OrderMethod {

        @Test
        @DisplayName("주문을 성공하면 유저에게 돈을 받고 주문을 완료한다.")
        void success() {
            // Given
            long userId = 1;
            long roomId = 1;
            long period = 5;
            LocalDate checkIn = timeProvider.getCurrentDate();
            LocalDate checkOut = timeProvider.getCurrentDate().plusDays(period);
            StayPeriod stayPeriod = new StayPeriod(checkIn, checkOut, timeProvider);

            given(userService.doesUserExist(userId)).willReturn(true);

            String hotelName = "호텔 이름";
            String roomType = "방 타입";
            String roomDescription = "방 설명";
            String roomNumber = "방 번호";
            Point dailyPoint = new Point(100);
            RoomSimpleResult roomSimpleResult = new RoomSimpleResult(hotelName, roomType,
                    roomDescription, roomNumber,
                    dailyPoint.getValue());
            given(placeReaderService.findRoomSimpleInfo(roomId))
                    .willReturn(new RoomSimpleResult(
                            hotelName, roomType, roomDescription, roomNumber, dailyPoint.getValue()
                    ));

            Point pointToPay = new Point(period * dailyPoint.getValue());
            long createdOrderId = 1;
            given(service.create(userId, stayPeriod, pointToPay)).willReturn(createdOrderId);

            // When
            OrderCreateResult actual = orderFacade.order(userId, roomId, stayPeriod);

            // Then
            assertThat(actual.orderId()).isEqualTo(createdOrderId);
        }

        @Test
        @DisplayName("방의 정보가 없다면 예외를 발생시킨다.")
        void failureForMissingRoomInfo() {
            // Given
            long userId = 1;
            long roomId = 2;
            StayPeriod stayPeriod = new StayPeriod(
                    timeProvider.getCurrentDate(),
                    timeProvider.getCurrentDate().plusDays(1),
                    timeProvider
            );

            given(placeReaderService.findRoomSimpleInfo(roomId)).willThrow(RuntimeException.class);

            // When & Then
            assertThatThrownBy(() -> orderFacade.order(userId, roomId, stayPeriod))
                    .isInstanceOf(RuntimeException.class);
        }

        @Test
        @DisplayName("유저의 돈이 없다면 예외를 발생시킨다.")
        void failureForMissingUserPoint() {
            // Given
            long userId = 1;
            long roomId = 1;
            StayPeriod stayPeriod = new StayPeriod(
                    timeProvider.getCurrentDate(),
                    timeProvider.getCurrentDate().plusDays(1),
                    timeProvider
            );

            given(userService.doesUserExist(userId)).willReturn(true);

            String hotelName = "호텔 이름";
            String roomType = "방 타입";
            String roomDescription = "방 설명";
            String roomNumber = "방 번호";
            Point dailyPoint = new Point(100);
            RoomSimpleResult roomSimpleResult = new RoomSimpleResult(hotelName, roomType,
                    roomDescription, roomNumber,
                    dailyPoint.getValue());
            given(placeReaderService.findRoomSimpleInfo(roomId))
                    .willReturn(new RoomSimpleResult(
                            hotelName, roomType, roomDescription, roomNumber, dailyPoint.getValue()
                    ));

            willThrow(IllegalStateException.class).given(userService)
                    .debitPoint(userId, dailyPoint);

            // When & Then
            assertThatThrownBy(() -> orderFacade.order(userId, roomId, stayPeriod))
                    .isInstanceOf(IllegalStateException.class);
        }
    }

    @Nested
    class CancelOrderMethod {

        @Test
        @DisplayName("주문 완료 상태에서 주문을 취소할 수 있다.")
        void success() {
            // Given
            long orderId = 1;
            long userId = 1;
            Point cancledPoint = new Point(100);
            StayPeriod canceledPeriod = new StayPeriod(timeProvider.getCurrentDate(),
                    timeProvider.getCurrentDate().plusDays(1),
                    timeProvider);
            given(service.cancel(orderId, userId)).willReturn(
                    new OrderCancelResult(cancledPoint, canceledPeriod));

            // When
            OrderCancelResult result = orderFacade.cancelOrder(orderId, userId);

            // Then
            assertThat(result.stayPeriod()).isEqualTo(canceledPeriod);
            assertThat(result.point()).isEqualTo(cancledPoint);
        }

        @Test
        @DisplayName("주문 완료 상태가 아니라면 주문을 취소할 수 없다.")
        void failureIfNotCompletedStatus() {
            // Given
            long orderId = 1;
            long userId = 1;
            Point cancledPoint = new Point(100);
            StayPeriod canceledPeriod = new StayPeriod(timeProvider.getCurrentDate(),
                    timeProvider.getCurrentDate().plusDays(1),
                    timeProvider);

            given(service.cancel(orderId, userId)).willThrow(IllegalStateException.class);

            // When & Then
            assertThatThrownBy(() -> orderFacade.cancelOrder(orderId, userId))
                    .isInstanceOf(IllegalStateException.class);
        }
    }
}
