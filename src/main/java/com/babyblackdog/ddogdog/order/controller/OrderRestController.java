package com.babyblackdog.ddogdog.order.controller;

import com.babyblackdog.ddogdog.common.date.StayPeriod;
import com.babyblackdog.ddogdog.common.date.TimeProvider;
import com.babyblackdog.ddogdog.order.controller.dto.RoomOrderPageResponse;
import com.babyblackdog.ddogdog.order.service.OrderFacade;
import com.babyblackdog.ddogdog.reservation.service.dto.result.RoomOrderPageResult;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
}
