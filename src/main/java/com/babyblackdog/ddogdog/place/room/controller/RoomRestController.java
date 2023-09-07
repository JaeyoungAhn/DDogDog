package com.babyblackdog.ddogdog.place.room.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.babyblackdog.ddogdog.common.date.StayPeriod;
import com.babyblackdog.ddogdog.common.date.TimeProvider;
import com.babyblackdog.ddogdog.place.room.controller.dto.RoomResponse;
import com.babyblackdog.ddogdog.place.room.controller.dto.RoomResponses;
import com.babyblackdog.ddogdog.place.room.service.RoomService;
import com.babyblackdog.ddogdog.place.room.service.dto.RoomResult;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/rooms", produces = APPLICATION_JSON_VALUE)
public class RoomRestController {

  private final RoomService roomService;
  private final TimeProvider timeProvider;

  public RoomRestController(RoomService roomService, TimeProvider timeProvider) {
    this.roomService = roomService;
    this.timeProvider = timeProvider;
  }

  @GetMapping(path = "/{roomId}")
  public ResponseEntity<RoomResponse> getRoomForDuration(
      @PathVariable Long roomId,
      @RequestParam LocalDate checkIn,
      @RequestParam LocalDate checkOut
  ) {
    StayPeriod stayPeriod = new StayPeriod(checkIn, checkOut, timeProvider);
    RoomResult roomResult = roomService.findRoomByIdForDuration(roomId, stayPeriod);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(RoomResponse.of(roomResult));
  }

  @GetMapping
  public ResponseEntity<RoomResponses> getRoomsOfHotelForDuration(
      @RequestParam Long hotelId,
      @RequestParam LocalDate checkIn,
      @RequestParam LocalDate checkOut,
      Pageable pageable
  ) {
    StayPeriod stayPeriod = new StayPeriod(checkIn, checkOut, timeProvider);
    Page<RoomResult> roomResults = roomService.findAllRoomsOfHotelForDuration(
        hotelId, stayPeriod, pageable);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(RoomResponses.of(roomResults));
  }

}
