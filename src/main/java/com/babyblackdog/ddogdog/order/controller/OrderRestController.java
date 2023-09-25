package com.babyblackdog.ddogdog.order.controller;

import com.babyblackdog.ddogdog.order.controller.dto.request.OrderCreateRequest;
import com.babyblackdog.ddogdog.order.controller.dto.response.OrderCancelResponse;
import com.babyblackdog.ddogdog.order.controller.dto.response.OrderCreateResponse;
import com.babyblackdog.ddogdog.order.controller.dto.response.OrderInformationResponse;
import com.babyblackdog.ddogdog.order.controller.dto.response.RoomOrderPageResponse;
import com.babyblackdog.ddogdog.order.service.OrderFacade;
import com.babyblackdog.ddogdog.order.service.OrderService;
import com.babyblackdog.ddogdog.order.service.dto.result.OrderCancelResult;
import com.babyblackdog.ddogdog.order.service.dto.result.OrderCreateResult;
import com.babyblackdog.ddogdog.order.service.dto.result.OrderInformationResult;
import com.babyblackdog.ddogdog.order.service.dto.result.RoomOrderPageResult;
import com.babyblackdog.ddogdog.reservation.service.StayPeriod;
import com.babyblackdog.ddogdog.reservation.service.TimeProvider;
import jakarta.validation.Valid;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderRestController {

    private final OrderFacade facade;
    private final OrderService service;
    private final TimeProvider timeProvider;

    public OrderRestController(OrderFacade facade, OrderService service,
            TimeProvider timeProvider) {
        this.facade = facade;
        this.service = service;
        this.timeProvider = timeProvider;
    }

    @GetMapping()
    public ResponseEntity<RoomOrderPageResponse> getRoomOrderPage(@RequestParam Long roomId,
            @RequestParam @DateTimeFormat(iso = ISO.DATE) LocalDate checkIn,
            @RequestParam @DateTimeFormat(iso = ISO.DATE) LocalDate checkOut) {
        StayPeriod stayPeriod = new StayPeriod(checkIn, checkOut, timeProvider);

        RoomOrderPageResult result = facade.findRoomSummary(roomId, stayPeriod);

        return ResponseEntity.status(HttpStatus.OK).body(RoomOrderPageResponse.of(result));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderCreateResponse> createOrder(
            @RequestBody @Valid OrderCreateRequest request) {
        StayPeriod stayPeriod = new StayPeriod(request.checkIn(), request.checkOut(), timeProvider);

        OrderCreateResult result = facade.order(request.roomId(), stayPeriod, request.couponReferenceId(),
                request.couponType());

        return ResponseEntity.status(HttpStatus.CREATED).body(OrderCreateResponse.of(result));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderInformationResponse> getOrderInformation(@PathVariable long orderId) {
        OrderInformationResult result = service.find(orderId);

        return ResponseEntity.status(HttpStatus.OK).body(OrderInformationResponse.of(result));
    }

    @PatchMapping("/cancel/{orderId}")
    public ResponseEntity<OrderCancelResponse> cancelOrder(@PathVariable Long orderId) {
        OrderCancelResult result = facade.cancelOrder(orderId);

        return ResponseEntity.status(HttpStatus.OK).body(OrderCancelResponse.of(result));
    }
}
