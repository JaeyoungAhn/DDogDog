package com.babyblackdog.ddogdog.reservation.service;

import com.babyblackdog.ddogdog.common.date.StayPeriod;
import com.babyblackdog.ddogdog.common.point.Point;
import com.babyblackdog.ddogdog.place.reader.PlaceReaderService;
import com.babyblackdog.ddogdog.place.reader.vo.RoomSimpleResult;
import com.babyblackdog.ddogdog.reservation.service.dto.result.OrderedReservationResult;
import com.babyblackdog.ddogdog.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReservationFacade {

  private final ReservationService service;
  private final PlaceReaderService placeReaderService;
  private final UserService userService;


  public ReservationFacade(ReservationService service, PlaceReaderService placeReaderService,
      UserService userService) {
    this.service = service;
    this.placeReaderService = placeReaderService;
    this.userService = userService;
  }
}
