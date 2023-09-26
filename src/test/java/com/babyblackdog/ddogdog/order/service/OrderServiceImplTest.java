package com.babyblackdog.ddogdog.order.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.babyblackdog.ddogdog.common.auth.Email;
import com.babyblackdog.ddogdog.common.auth.JwtSimpleAuthentication;
import com.babyblackdog.ddogdog.common.point.Point;
import com.babyblackdog.ddogdog.global.exception.ErrorCode;
import com.babyblackdog.ddogdog.global.exception.OrderException;
import com.babyblackdog.ddogdog.order.domain.Order;
import com.babyblackdog.ddogdog.order.domain.OrderRepository;
import com.babyblackdog.ddogdog.order.domain.OrderStatus;
import com.babyblackdog.ddogdog.order.service.dto.result.OrderInformationResult;
import com.babyblackdog.ddogdog.reservation.service.StayPeriod;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderServiceImpl;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private JwtSimpleAuthentication authentication;

    @Nested
    class CreateMethod {

        @DisplayName("주문 생성에 성공하면 주문 ID를 반환한다.")
        @ParameterizedTest
        @MethodSource("com.babyblackdog.ddogdog.order.domain.OrderTest#provideValidArgumentsForConstructor")
        void success(Email email, StayPeriod stayPeriod, Point point) {
            // Given
            given(authentication.getEmail()).willReturn(email);
            given(orderRepository.save(any())).willReturn(
                    new Order(email, stayPeriod, point));

            // When & Then
            assertThatCode(() -> orderServiceImpl.create(stayPeriod, point))
                    .doesNotThrowAnyException();
        }
    }

    @Nested
    class CompleteMethod {

        @DisplayName("주문이 존재하지 않는 경우 예외가 발생한다.")
        @Test
        void orderNotFound_throwsException() {
            // Given
            Long orderId = 1L;
            given(orderRepository.findById(orderId)).willReturn(Optional.empty());

            // When & Then
            assertThatThrownBy(() -> {
                orderServiceImpl.complete(orderId);
            })
                    .isInstanceOf(OrderException.class)
                    .hasMessageContaining(ErrorCode.ORDER_NOT_FOUND.getMessage());
        }

        @DisplayName("주문 완료에 성공한다.")
        @ParameterizedTest
        @MethodSource("com.babyblackdog.ddogdog.order.domain.OrderTest#provideValidArgumentsForConstructor")
        void completeOrderSuccess(Email email, StayPeriod stayPeriod, Point point) {
            // Given
            Long orderId = 1L;
            Order order = new Order(email, stayPeriod, point);
            given(orderRepository.findById(orderId)).willReturn(Optional.of(order));

            // When
            orderServiceImpl.complete(orderId);

            // Then
            assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.COMPLETED);
        }
    }

    @Nested
    class FindMethod {

        @DisplayName("주문을 찾지 못한 경우 예외가 발생한다.")
        @Test
        void orderNotFound_throwsException() {
            // Given
            Long orderId = 1L;
            given(orderRepository.findById(orderId)).willReturn(Optional.empty());

            // When & Then
            assertThatThrownBy(() -> {
                orderServiceImpl.find(orderId);
            }).isInstanceOf(OrderException.class)
                    .hasMessageContaining(ErrorCode.ORDER_NOT_FOUND.getMessage());
        }

        @DisplayName("주문을 성공적으로 찾는다.")
        @ParameterizedTest
        @MethodSource("com.babyblackdog.ddogdog.order.domain.OrderTest#provideValidArgumentsForConstructor")
        void findOrderSuccess(Email email, StayPeriod stayPeriod, Point point) {
            // Given
            long orderId = 1L;
            Order order = new Order(email, stayPeriod, point);

            given(orderRepository.findById(orderId)).willReturn(Optional.of(order));
            given(authentication.getEmail()).willReturn(email);

            // When
            OrderInformationResult result = orderServiceImpl.find(orderId);

            // Then
            assertThat(result.orderStatus()).isEqualTo(order.getOrderStatus().getDescription());
            assertThat(result.stayPeriod()).isEqualTo(order.getStayPeriod());
            assertThat(result.usedPoint()).isEqualTo(order.getUsedPoint());
        }
    }
}
