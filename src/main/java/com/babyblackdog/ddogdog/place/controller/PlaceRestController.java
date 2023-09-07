package com.babyblackdog.ddogdog.place.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.babyblackdog.ddogdog.common.date.StayPeriod;
import com.babyblackdog.ddogdog.common.date.TimeProvider;
import com.babyblackdog.ddogdog.place.controller.dto.request.AddHotelRequest;
import com.babyblackdog.ddogdog.place.controller.dto.response.HotelResponse;
import com.babyblackdog.ddogdog.place.controller.dto.response.HotelResponses;
import com.babyblackdog.ddogdog.place.controller.dto.response.RoomResponse;
import com.babyblackdog.ddogdog.place.controller.dto.response.RoomResponses;
import com.babyblackdog.ddogdog.place.hotel.model.vo.Province;
import com.babyblackdog.ddogdog.place.hotel.service.HotelService;
import com.babyblackdog.ddogdog.place.hotel.service.dto.HotelResult;
import com.babyblackdog.ddogdog.place.room.service.RoomService;
import com.babyblackdog.ddogdog.place.room.service.dto.RoomResult;
import java.net.URI;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
class PlaceRestController {

  private final HotelService hotelService;
  private final RoomService roomService;
  private final TimeProvider timeProvider;

  PlaceRestController(HotelService hotelService, RoomService roomService,
      TimeProvider timeProvider) {
    this.hotelService = hotelService;
    this.roomService = roomService;
    this.timeProvider = timeProvider;
  }

  /**
   * 숙소 정보 등록 POST /places
   *
   * @param request
   * @return
   */
  @PostMapping(consumes = APPLICATION_JSON_VALUE)
  public ResponseEntity<HotelResponse> addHotel(@Validated @RequestBody AddHotelRequest request) {
    HotelResult hotelResult = hotelService.registerHotel(AddHotelRequest.to(request));
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{hotelId}")
        .buildAndExpand(hotelResult.id())
        .toUri();
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .location(location)
        .body(HotelResponse.of(hotelResult));
  }

  /**
   * 숙소 아이디로 해당 숙소 조회 GET /places/{hotelId}
   *
   * @param hotelId
   * @return ResponseEntity<HotelResponse>
   */
  @GetMapping("/{hotelId}")
  public ResponseEntity<HotelResponse> getHotel(@PathVariable Long hotelId) {
    HotelResult result = hotelService.findHotelById(hotelId);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(HotelResponse.of(result));
  }

  /**
   * 지역 내 있는 모든 숙소 정보를 조회 GET /places?province=강원&page=0&size=5
   *
   * @param province
   * @param pageable
   * @return ResponseEntity<HotelResponses>
   */
  @GetMapping
  public ResponseEntity<HotelResponses> getHotelsInProvince(@RequestParam String province,
      Pageable pageable) {
    Page<HotelResult> result = hotelService.findHotelsInProvince(new Province(province), pageable);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(HotelResponses.of(result));
  }

  /**
   * 숙소 아이디와 객실 아이디로 특정 기간 동안의 객실 정보 조회 GET /places/{hotelId}/{roomId}?checkIn=날짜&checkOut=날짜
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
        .status(HttpStatus.OK)
        .body(RoomResponse.of(roomResult));
  }

  /**
   * 숙소 아이디로 특정 기간 동안 해당 숙소에 모든 객실 정보를 조회 GET
   * /places/{hotelId}/rooms?checkIn=날짜&checkOut=날짜&page=0&size=5
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
        .status(HttpStatus.OK)
        .body(RoomResponses.of(roomResults));
  }

}
