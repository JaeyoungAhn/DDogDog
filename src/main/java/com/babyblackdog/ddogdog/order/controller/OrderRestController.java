package com.babyblackdog.ddogdog.order.controller;

import com.babyblackdog.ddogdog.common.date.StayPeriod;
import com.babyblackdog.ddogdog.common.date.TimeProvider;
import com.babyblackdog.ddogdog.order.controller.dto.response.OrderCreateResponse;
import com.babyblackdog.ddogdog.order.controller.dto.response.RoomOrderPageResponse;
import com.babyblackdog.ddogdog.order.service.OrderFacade;
import com.babyblackdog.ddogdog.order.service.dto.result.OrderCreateResult;
import com.babyblackdog.ddogdog.order.controller.dto.request.OrderCreateRequest;
import com.babyblackdog.ddogdog.order.service.dto.result.RoomOrderPageResult;
import jakarta.validation.Valid;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderRestController {

    private final OrderFacade facade;
    private final TimeProvider timeProvider;

    public OrderRestController(OrderFacade facade, TimeProvider timeProvider) {
        this.facade = facade;
        this.timeProvider = timeProvider;
    }

    @GetMapping()
    public ResponseEntity<RoomOrderPageResponse> getRoomOrderPage(@RequestParam Long roomId,
            @RequestParam @DateTimeFormat(iso = ISO.DATE) LocalDate checkIn,
            @RequestParam @DateTimeFormat(iso = ISO.DATE) LocalDate checkOut) {
        StayPeriod stayPeriod = new StayPeriod(checkIn, checkOut, timeProvider);

        RoomOrderPageResult result = facade.findRoomSummary(roomId, stayPeriod);

        return ResponseEntity.status(HttpStatus.OK)
                .body(RoomOrderPageResponse.of(result));
    }

    @PostMapping()
    public ResponseEntity<OrderCreateResponse> createOrder(
            @RequestBody @Valid OrderCreateRequest request) {
        StayPeriod stayPeriod = new StayPeriod(request.checkIn(), request.checkOut(), timeProvider);

        OrderCreateResult result = facade.order(request.userId(), request.roomId(), stayPeriod);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(OrderCreateResponse.of(result));
    }
}
