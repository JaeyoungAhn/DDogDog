package com.babyblackdog.ddogdog.order.domain;

import org.junit.jupiter.api.Disabled;

@Disabled
class OrderTest {

//    private final static TimeProvider TIME_PROVIDER = new TodayDateProvider();
//
//    @Nested
//    class Constructor {
//
//        @ParameterizedTest
//        @MethodSource("com.babyblackdog.ddogdog.order.domain.OrderTest#provideValidArgumentsForConstructor")
//        @DisplayName("유효한 값이라면 객체를 생성한다.")
//        void success(Email userEmail, StayPeriod stayPeriod, Point usedPoint) {
//            // Given
//            LocalDate expectedCheckIn = stayPeriod.getCheckIn();
//            LocalDate expectedCheckOut = stayPeriod.getCheckOut();
//            long expectedPeriod = stayPeriod.getPeriod();
//
//            // WHen
//            Order order = new Order(userEmail, stayPeriod, usedPoint);
//
//            // Then
//            assertThat(order).isNotNull();
//            assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.PREPARED);
//            assertThat(order.getStayPeriod().getCheckIn()).isEqualTo(expectedCheckIn);
//            assertThat(order.getStayPeriod().getCheckOut()).isEqualTo(expectedCheckOut);
//            assertThat(order.getStayPeriod().getPeriod()).isEqualTo(expectedPeriod);
//        }
//
//        @ParameterizedTest
//        @MethodSource("com.babyblackdog.ddogdog.order.domain.OrderTest#provideInvalidArgumentsForConstructor")
//        @DisplayName("1개라도 null이라면 예외를 발생시킨다.")
//        void failed(Email userEmail, StayPeriod stayPeriod, Point usedPoint) {
//            // When & Then
//            assertThatThrownBy(() -> new Order(userEmail, stayPeriod, usedPoint))
//                    .isInstanceOf(NullPointerException.class);
//        }
//    }
//
//    @Nested
//    class CompleteMethod {
//
//        @ParameterizedTest
//        @MethodSource("com.babyblackdog.ddogdog.order.domain.OrderTest#provideValidArgumentsForConstructor")
//        @DisplayName("주문 대기 상태라면 주문 성공으로 넘어갈 수 있다.")
//        void success(Email userEmail, StayPeriod stayPeriod, Point usedPoint) {
//            // Given
//            Order order = new Order(userEmail, stayPeriod, usedPoint);
//
//            // When & Then
//            assertThatCode(order::complete).doesNotThrowAnyException();
//            assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.COMPLETED);
//        }
//
//        @ParameterizedTest
//        @MethodSource("com.babyblackdog.ddogdog.order.domain.OrderTest#provideValidArgumentsForConstructor")
//        @DisplayName("주문 대기가 아니라면 예외를 발생시킨다.")
//        void failed(Email userEmail, StayPeriod stayPeriod, Point usedPoint) {
//            // Given
//            Order order = new Order(userEmail, stayPeriod, usedPoint);
//            order.complete();
//
//            // When & Then
//            assertThatThrownBy(order::complete)
//                    .isInstanceOf(IllegalStateException.class);
//        }
//    }
//
//    @ParameterizedTest
//    @MethodSource("com.babyblackdog.ddogdog.order.domain.OrderTest#provideValidArgumentsForConstructor")
//    @DisplayName("주문한 아이디와 동일하다면 true를 반환한다.")
//    void isOrderAuthorValid_success(Email userEmail, StayPeriod stayPeriod, Point usedPoint) {
//        // Given
//        Order order = new Order(userEmail, stayPeriod, usedPoint);
//
//        // When & Then
//        assertThat(order.isOrderAuthorValid(userEmail)).isTrue();
//    }
//
//    @Nested
//    class CancelMethod {
//
//        @ParameterizedTest
//        @MethodSource("com.babyblackdog.ddogdog.order.domain.OrderTest#provideValidArgumentsForConstructor")
//        @DisplayName("주문 취소가 가능한 상태라면 주문 취소를 한다.")
//        void success(Email userEmail, StayPeriod stayPeriod, Point usedPoint) {
//            // Given
//            Order order = new Order(userEmail, stayPeriod, usedPoint);
//            order.complete();
//
//            // When & Then
//            assertThatCode(() -> order.cancel(userEmail)).doesNotThrowAnyException();
//            assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.CANCELED);
//        }
//
//        @ParameterizedTest
//        @MethodSource("com.babyblackdog.ddogdog.order.domain.OrderTest#provideValidArgumentsForConstructor")
//        @DisplayName("주문 취소가 불가능한 상태라면 예외를 발생시킨다.")
//        void failedToNotOrderAuthor(Email userEmail, StayPeriod stayPeriod, Point usedPoint) {
//            // Given
//            Order order = new Order(userEmail, stayPeriod, usedPoint);
//            order.complete();
//
//            // When & Then
//            assertThatThrownBy(() -> order.cancel(userEmail))
//                    .isInstanceOf(IllegalStateException.class);
//        }
//
//        @ParameterizedTest
//        @MethodSource("com.babyblackdog.ddogdog.order.domain.OrderTest#provideValidArgumentsForConstructor")
//        @DisplayName("주문 취소가 불가능한 상태라면 예외를 발생시킨다.")
//        void failedToInvalidState(Long userId, StayPeriod stayPeriod, Point usedPoint) {
//            // Given
//            Order order = new Order(userId, stayPeriod, usedPoint);
//
//            // When & Then
//            assertThatThrownBy(() -> order.cancel(userId))
//                    .isInstanceOf(IllegalStateException.class);
//        }
//    }
//
//    private static Stream<Arguments> provideValidArgumentsForConstructor() {
//        Email userEmail = new Email("honggildong@naver.com");
//        LocalDate today = TIME_PROVIDER.getCurrentDate();
//        LocalDate tomorrow = TIME_PROVIDER.getCurrentDate().plusDays(5);
//        StayPeriod stayPeriod = new StayPeriod(today, tomorrow, TIME_PROVIDER);
//
//        Point usedPoint = new Point(500);
//
//        return Stream.of(
//                Arguments.arguments(userEmail, stayPeriod, usedPoint)
//        );
//    }
//
//    private static Stream<Arguments> provideInvalidArgumentsForConstructor() {
//        Email userEmail = new Email("honggildong@naver.com");
//        LocalDate today = TIME_PROVIDER.getCurrentDate();
//        LocalDate tomorrow = TIME_PROVIDER.getCurrentDate().plusDays(1);
//        StayPeriod stayPeriod = new StayPeriod(today, tomorrow, TIME_PROVIDER);
//
//        Point usedPoint = new Point(100);
//
//        return Stream.of(
//                Arguments.arguments(null, stayPeriod, usedPoint),
//                Arguments.arguments(userEmail, null, usedPoint),
//                Arguments.arguments(userEmail, stayPeriod, null)
//        );
//    }
}
