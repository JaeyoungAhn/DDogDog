package com.babyblackdog.ddogdog.reservation.controller;

import com.babyblackdog.ddogdog.reservation.controller.dto.request.ReservationOrderRequest;
import com.babyblackdog.ddogdog.reservation.controller.dto.response.OrderedReservationResponse;
import com.babyblackdog.ddogdog.reservation.controller.dto.response.RoomOrderPageResponse;
import com.babyblackdog.ddogdog.reservation.service.ReservationFacade;
import com.babyblackdog.ddogdog.reservation.service.TimeProvider;
import com.babyblackdog.ddogdog.reservation.service.dto.StayPeriod;
import com.babyblackdog.ddogdog.reservation.service.dto.result.OrderedReservationResult;
import com.babyblackdog.ddogdog.reservation.service.dto.result.RoomOrderPageResult;
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
@RequestMapping(value = "/reservations", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReservationController {

  private final ReservationFacade facade;
  private final TimeProvider timeProvider;

  public ReservationController(ReservationFacade facade, TimeProvider timeProvider) {
    this.facade = facade;
    this.timeProvider = timeProvider;
  }

  @GetMapping()
  public ResponseEntity<RoomOrderPageResponse> getRoomOrderPageInfo(
      @RequestParam Long placeId, @RequestParam Long roomId,
      @RequestParam @DateTimeFormat(iso = ISO.DATE) LocalDate checkIn,
      @RequestParam @DateTimeFormat(iso = ISO.DATE) LocalDate checkOut) {
    StayPeriod stayPeriod = new StayPeriod(checkIn, checkOut, timeProvider);

    RoomOrderPageResult result = facade.findRoomInfo(placeId, roomId, stayPeriod);

    return ResponseEntity.status(HttpStatus.OK)
        .body(RoomOrderPageResponse.of(result));
  }

  @PostMapping()
  public ResponseEntity<OrderedReservationResponse> createReservation(
      @RequestBody @Valid ReservationOrderRequest request) {
    StayPeriod stayPeriod = new StayPeriod(request.checkIn(), request.checkOut(), timeProvider);

    OrderedReservationResult result = facade.order(request.userId(),
        request.placeId(), request.roomId(), stayPeriod);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(OrderedReservationResponse.of(result));
  }
}
