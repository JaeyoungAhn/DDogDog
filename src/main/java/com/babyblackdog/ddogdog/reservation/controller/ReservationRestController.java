package com.babyblackdog.ddogdog.reservation.controller;

import com.babyblackdog.ddogdog.common.date.StayPeriod;
import com.babyblackdog.ddogdog.common.date.TimeProvider;
import com.babyblackdog.ddogdog.reservation.controller.dto.request.ReservationOrderRequest;
import com.babyblackdog.ddogdog.reservation.controller.dto.response.OrderedReservationResponse;
import com.babyblackdog.ddogdog.reservation.service.ReservationFacade;
import com.babyblackdog.ddogdog.reservation.service.dto.result.OrderedReservationResult;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/reservations", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReservationRestController {

  private final ReservationFacade facade;
  private final TimeProvider timeProvider;

  public ReservationRestController(ReservationFacade facade, TimeProvider timeProvider) {
    this.facade = facade;
    this.timeProvider = timeProvider;
  }

  @PostMapping()
  public ResponseEntity<OrderedReservationResponse> createReservation(
      @RequestBody @Valid ReservationOrderRequest request) {
    StayPeriod stayPeriod = new StayPeriod(request.checkIn(), request.checkOut(), timeProvider);

    OrderedReservationResult result = facade.order(request.userId(), request.roomId(), stayPeriod);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(OrderedReservationResponse.of(result));
  }
}
