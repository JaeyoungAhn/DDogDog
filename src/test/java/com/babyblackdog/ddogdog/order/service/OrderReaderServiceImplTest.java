package com.babyblackdog.ddogdog.order.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.babyblackdog.ddogdog.common.auth.Email;
import com.babyblackdog.ddogdog.common.auth.JwtSimpleAuthentication;
import com.babyblackdog.ddogdog.order.domain.Order;
import com.babyblackdog.ddogdog.order.domain.OrderRepository;
import com.babyblackdog.ddogdog.order.domain.OrderStatus;
import com.babyblackdog.ddogdog.reservation.service.StayPeriod;
import com.babyblackdog.ddogdog.reservation.service.TimeProvider;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class OrderReaderServiceImplTest {

    @MockBean
    JwtSimpleAuthentication authentication;

    @MockBean
    OrderRepository orderRepository;

    @Autowired
    TimeProvider timeProvider;

    @Autowired
    OrderReaderServiceImpl orderReaderService;

    @Nested
    @DisplayName("isStayOver 메소드 테스트")
    class IsStayOverTest {

        @Test
        @DisplayName("모든 조건이 충족되면 true를 반환한다.")
        void allConditionsMet() {
            // Given
            long orderId = 1L;
            Order mockOrder = mock(Order.class);

            given(orderRepository.findById(orderId)).willReturn(Optional.of(mockOrder));

            Email email = new Email("test@test.org");
            given(authentication.getEmail()).willReturn(email);
            given(mockOrder.isOrderAuthorValid(email)).willReturn(true);

            given(mockOrder.getOrderStatus()).willReturn(OrderStatus.COMPLETED);

            StayPeriod mockStayPeriod = mock(StayPeriod.class);
            given(mockStayPeriod.getCheckOut()).willReturn(timeProvider.getCurrentDate());
            given(mockOrder.getStayPeriod()).willReturn(mockStayPeriod);

            // When
            boolean result = orderReaderService.isStayOver(orderId);

            // Then
            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("주문이 존재하지 않으면 false를 반환한다.")
        public void orderNotFound() {
            // Given
            long orderId = 1L;

            given(orderRepository.findById(orderId)).willReturn(Optional.empty());

            // When
            boolean result = orderReaderService.isStayOver(1L);

            // Then
            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("주문자 이메일이 일치하지 않으면 false를 반환한다.")
        void authorEmailNotMatching() {
            // Given
            long orderId = 1L;
            Order mockOrder = mock(Order.class);

            given(orderRepository.findById(orderId)).willReturn(Optional.of(mockOrder));

            Email email = new Email("test@test.org");
            given(authentication.getEmail()).willReturn(email);
            given(mockOrder.isOrderAuthorValid(email)).willReturn(false);

            // When
            boolean result = orderReaderService.isStayOver(orderId);

            // Then
            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("주문 상태가 완료되지 않았으면 false를 반환한다.")
        void orderStatusNotCompleted() {
            // Given
            long orderId = 1L;
            Order mockOrder = mock(Order.class);

            given(orderRepository.findById(orderId)).willReturn(Optional.of(mockOrder));

            Email email = new Email("test@test.org");
            given(authentication.getEmail()).willReturn(email);
            given(mockOrder.isOrderAuthorValid(email)).willReturn(true);

            given(mockOrder.getOrderStatus()).willReturn(OrderStatus.PREPARED);

            // When
            boolean result = orderReaderService.isStayOver(orderId);

            // Then
            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("날짜가 지나지 않았으면 false를 반환한다.")
        void dateNotPassedReturnsFalse() {
            // Given
            Long orderId = 1L;
            Order mockOrder = mock(Order.class);
            Email email = new Email("test@test.org");

            given(orderRepository.findById(orderId)).willReturn(Optional.of(mockOrder));
            given(authentication.getEmail()).willReturn(email);
            given(mockOrder.isOrderAuthorValid(email)).willReturn(true);
            given(mockOrder.getOrderStatus()).willReturn(OrderStatus.COMPLETED);

            LocalDate futureDate = LocalDate.now().plusDays(1);
            StayPeriod mockStayPeriod = mock(StayPeriod.class);
            given(mockStayPeriod.getCheckOut()).willReturn(futureDate);
            given(mockOrder.getStayPeriod()).willReturn(mockStayPeriod);

            // When
            boolean result = orderReaderService.isStayOver(orderId);

            // Then
            assertThat(result).isFalse();
        }
    }
}
