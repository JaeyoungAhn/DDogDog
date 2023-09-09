package com.babyblackdog.ddogdog.place.hotel.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.babyblackdog.ddogdog.place.facade.PlaceFacadeService;
import com.babyblackdog.ddogdog.place.hotel.controller.dto.AddHotelRequest;
import com.babyblackdog.ddogdog.place.hotel.controller.dto.HotelResponse;
import com.babyblackdog.ddogdog.place.hotel.controller.dto.HotelResponses;
import com.babyblackdog.ddogdog.place.hotel.model.vo.Province;
import com.babyblackdog.ddogdog.place.hotel.service.HotelService;
import com.babyblackdog.ddogdog.place.hotel.service.dto.HotelResult;
import java.net.URI;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class HotelRestController {

  private final HotelService hotelService;
  private final PlaceFacadeService placeService;

  public HotelRestController(HotelService hotelService, PlaceFacadeService placeService) {
    this.hotelService = hotelService;
    this.placeService = placeService;
  }

  /**
   * POST /places [숙소 정보 등록]
   *
   * @param request
   * @return
   */
  @PostMapping(consumes = APPLICATION_JSON_VALUE)
  public ResponseEntity<HotelResponse> addHotel(@Validated @RequestBody AddHotelRequest request) {
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

  @DeleteMapping(path = "/{hotelId}")
  public ResponseEntity<Void> removeHotel(@PathVariable Long hotelId) {
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
  public ResponseEntity<HotelResponse> getHotel(@PathVariable Long hotelId) {
    HotelResult result = hotelService.findHotelById(hotelId);
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
  public ResponseEntity<HotelResponses> getHotelsInProvince(@RequestParam String province,
      Pageable pageable) {
    Page<HotelResult> result = hotelService.findHotelsInProvince(new Province(province), pageable);
    return ResponseEntity
        .status(OK)
        .body(HotelResponses.of(result));
  }

}
