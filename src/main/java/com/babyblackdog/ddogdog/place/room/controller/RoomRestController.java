package com.babyblackdog.ddogdog.place.room.controller;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.babyblackdog.ddogdog.common.date.StayPeriod;
import com.babyblackdog.ddogdog.common.date.TimeProvider;
import com.babyblackdog.ddogdog.place.facade.PlaceFacadeService;
import com.babyblackdog.ddogdog.place.room.controller.dto.AddRoomRequest;
import com.babyblackdog.ddogdog.place.room.controller.dto.RoomResponse;
import com.babyblackdog.ddogdog.place.room.controller.dto.RoomResponses;
import com.babyblackdog.ddogdog.place.room.service.RoomService;
import com.babyblackdog.ddogdog.place.room.service.dto.RoomResult;
import java.net.URI;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(path = "/places", produces = APPLICATION_JSON_VALUE)
public class RoomRestController {

  private final RoomService roomService;
  private final PlaceFacadeService placeService;
  private final TimeProvider timeProvider;

  public RoomRestController(RoomService roomService, PlaceFacadeService placeService, TimeProvider timeProvider) {
    this.roomService = roomService;
    this.placeService = placeService;
    this.timeProvider = timeProvider;
  }

  /**
   * POST /places/{hotelId} [호텔에 대한 객실 정보 추가]
   * @param hotelId
   * @param request
   * @return
   */
  @PostMapping(path = "/{hotelId}")
  public ResponseEntity<RoomResponse> createRoomOfHotel(
      @PathVariable Long hotelId,
      @Validated @RequestBody AddRoomRequest request
  ) {
    RoomResult roomResult = placeService.registerRoomOfHotel(AddRoomRequest.to(hotelId, request));
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{hotelId}/{roomId}")
        .buildAndExpand(hotelId, roomResult.id())
        .toUri();
    return ResponseEntity
        .status(CREATED)
        .location(location)
        .body(RoomResponse.of(roomResult));
  }

  /**
   * GET /places/{hotelId}/{roomId}?checkIn=날짜&checkOut=날짜 [숙소 아이디와 객실 아이디로 특정 기간 동안의 객실 정보 조회]
   *
   * @param hotelId
   * @param roomId
   * @param checkIn
   * @param checkOut
   * @return ResponseEntity<RoomResponse>\
   */
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
        .status(OK)
        .body(RoomResponse.of(roomResult));
  }

  /**
   * GET /places/{hotelId}/rooms?checkIn=날짜&checkOut=날짜&page=0&size=5
   * [숙소 아이디로 특정 기간 동안 해당 숙소에 모든 객실 정보를 조회]
   *
   * @param hotelId
   * @param checkIn
   * @param checkOut
   * @param pageable
   * @return ResponseEntity<RoomResponse>
   */
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
        .status(OK)
        .body(RoomResponses.of(roomResults));
  }

}
