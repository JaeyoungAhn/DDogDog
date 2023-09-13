package com.babyblackdog.ddogdog.place.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.babyblackdog.ddogdog.common.date.StayPeriod;
import com.babyblackdog.ddogdog.common.date.TimeProvider;
import com.babyblackdog.ddogdog.place.controller.dto.AddHotelRequest;
import com.babyblackdog.ddogdog.place.controller.dto.AddRoomRequest;
import com.babyblackdog.ddogdog.place.controller.dto.HotelResponse;
import com.babyblackdog.ddogdog.place.controller.dto.HotelResponses;
import com.babyblackdog.ddogdog.place.controller.dto.RoomResponse;
import com.babyblackdog.ddogdog.place.controller.dto.RoomResponses;
import com.babyblackdog.ddogdog.place.model.vo.Province;
import com.babyblackdog.ddogdog.place.service.PlaceService;
import com.babyblackdog.ddogdog.place.service.dto.HotelResult;
import com.babyblackdog.ddogdog.place.service.dto.RoomResult;
import java.net.URI;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
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
public class PlaceRestController {

  private final PlaceService placeService;
  private final TimeProvider timeProvider;

  public PlaceRestController(PlaceService placeService, TimeProvider timeProvider) {
    this.placeService = placeService;
    this.timeProvider = timeProvider;
  }

  /**
   * POST /places [숙소 정보 등록]
   *
   * @param request
   * @return ResponseEntity<HotelResponse>
   */
  @PostMapping(consumes = APPLICATION_JSON_VALUE)
  public ResponseEntity<HotelResponse> addHotel(
      @Validated @RequestBody AddHotelRequest request
  ) {
    HotelResult hotelResult = placeService.registerHotel(AddHotelRequest.to(request));
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{hotelId}")
        .buildAndExpand(hotelResult.id())
        .toUri();
    return ResponseEntity
        .status(CREATED)
        .location(location)
        .body(HotelResponse.of(hotelResult));
  }

  /**
   * 숙소를 제거한다. 숙소에 달린 객실도 모두 제거한다. DELETE /places/{hotelId}
   *
   * @param hotelId
   * @return ResponseEntity<Void> : 204
   */
  @DeleteMapping(path = "/{hotelId}")
  public ResponseEntity<Void> removeHotel(
      @PathVariable Long hotelId
  ) {
    placeService.deleteHotel(hotelId);
    return ResponseEntity
        .status(NO_CONTENT)
        .build();
  }

  /**
   * GET /places/{hotelId} [숙소 아이디로 해당 숙소 조회]
   *
   * @param hotelId
   * @return ResponseEntity<HotelResponse>
   */
  @GetMapping("/{hotelId}")
  public ResponseEntity<HotelResponse> getHotel(
      @PathVariable Long hotelId
  ) {
    HotelResult result = placeService.findHotel(hotelId);
    System.out.println(SecurityContextHolder.getContext().getAuthentication());
    return ResponseEntity
        .status(OK)
        .body(HotelResponse.of(result));
  }

  /**
   * GET /places?province=강원&page=0&size=5 [지역 내 있는 모든 숙소 정보를 조회]
   *
   * @param province
   * @param pageable
   * @return ResponseEntity<HotelResponses>
   */
  @GetMapping
  public ResponseEntity<HotelResponses> getHotelsInProvince(
      @RequestParam String province, Pageable pageable
  ) {
    Page<HotelResult> result = placeService.findHotelsInProvince(new Province(province), pageable);
    return ResponseEntity
        .status(OK)
        .body(HotelResponses.of(result));
  }

  /**
   * POST /places/{hotelId} [호텔에 대한 객실 정보 추가]
   *
   * @param hotelId
   * @param request
   * @return
   */
  @PostMapping(path = "/{hotelId}", consumes = APPLICATION_JSON_VALUE)
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
   * DELETE /places/{hotelId}/{roomId} 숙소의 객실을 제거한다.
   *
   * @param hotelId
   * @param roomId
   * @return ResponseEntity<Void>
   */
  @DeleteMapping(path = "/{hotelId}/{roomId}")
  public ResponseEntity<Void> removeRoomOfHotel(@PathVariable Long hotelId,
      @PathVariable Long roomId) {
    placeService.deleteRoom(roomId);
    return ResponseEntity
        .status(NO_CONTENT)
        .build();
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
    RoomResult roomResult = placeService.findRoomForDuration(roomId, stayPeriod);
    return ResponseEntity
        .status(OK)
        .body(RoomResponse.of(roomResult));
  }

  /**
   * GET /places/{hotelId}/rooms?checkIn=날짜&checkOut=날짜&page=0&size=5 [숙소 아이디로 특정 기간 동안 해당 숙소에 모든 객실
   * 정보를 조회]
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
    Page<RoomResult> roomResults = placeService.findAllRoomsOfHotelForDuration(
        hotelId, stayPeriod, pageable);
    return ResponseEntity
        .status(OK)
        .body(RoomResponses.of(roomResults));
  }

}
