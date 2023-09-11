package com.babyblackdog.ddogdog.reservation.controller;

import com.babyblackdog.ddogdog.common.date.TimeProvider;
import com.babyblackdog.ddogdog.reservation.service.ReservationFacade;
import org.springframework.http.MediaType;
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
}
