package com.babyblackdog.ddogdog.place.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.babyblackdog.ddogdog.common.date.StayPeriod;
import com.babyblackdog.ddogdog.common.date.TimeProvider;
import com.babyblackdog.ddogdog.place.hotel.controller.dto.HotelResponse;
import com.babyblackdog.ddogdog.place.hotel.controller.dto.HotelResponses;
import com.babyblackdog.ddogdog.place.hotel.model.vo.Province;
import com.babyblackdog.ddogdog.place.hotel.service.HotelService;
import com.babyblackdog.ddogdog.place.hotel.service.dto.HotelResult;
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
@RequestMapping(path = "/places", produces = APPLICATION_JSON_VALUE)
class PlaceRestController {

  private final HotelService hotelService;
  private final RoomService roomService;
  private final TimeProvider timeProvider;

  PlaceRestController(HotelService hotelService, RoomService roomService, TimeProvider timeProvider) {
    this.hotelService = hotelService;
    this.roomService = roomService;
    this.timeProvider = timeProvider;
  }

  // GET /places/{hotelId}
  @GetMapping("/{hotelId}")
  public ResponseEntity<HotelResponse> getHotel(@PathVariable Long hotelId) {
    HotelResult result = hotelService.findHotelById(hotelId);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(HotelResponse.of(result));
  }

  // GET /places?province=강원
  @GetMapping
  public ResponseEntity<HotelResponses> getHotelsInProvince(@RequestParam String province,
      Pageable pageable) {
    Page<HotelResult> result = hotelService.findHotelsInProvince(new Province(province), pageable);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(HotelResponses.of(result));
  }

  // GET /places/{hotelId}/{roomId}?checkIn=&checkOut=
  @GetMapping(path = "/{hotelId}/{roomId}")
  public ResponseEntity<RoomResponse> getRoomForDuration(
      @PathVariable Long hotelId,
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

  // GET /places/{hotelId}/rooms?checkIn=&checkOut=
  @GetMapping(path = "/{hotelId}/rooms")
  public ResponseEntity<RoomResponses> getRoomsOfHotelForDuration(
      @PathVariable Long hotelId,
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
