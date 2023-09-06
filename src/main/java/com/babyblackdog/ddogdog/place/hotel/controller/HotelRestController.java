package com.babyblackdog.ddogdog.place.hotel.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.babyblackdog.ddogdog.place.hotel.controller.dto.HotelResponse;
import com.babyblackdog.ddogdog.place.hotel.controller.dto.HotelResponses;
import com.babyblackdog.ddogdog.place.hotel.model.vo.Province;
import com.babyblackdog.ddogdog.place.hotel.service.HotelService;
import com.babyblackdog.ddogdog.place.hotel.service.dto.HotelResult;
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
@RequestMapping(path = "/hotels", produces = APPLICATION_JSON_VALUE)
public class HotelRestController {

  private final HotelService hotelService;

  public HotelRestController(HotelService hotelService) {
    this.hotelService = hotelService;
  }

  @GetMapping
  public ResponseEntity<HotelResponses> getPlacesByProvince(@RequestParam String province,
      Pageable pageable) {
    Page<HotelResult> result = hotelService.findHotelByProvince(new Province(province), pageable);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(HotelResponses.of(result));
  }

  @GetMapping("/{placeId}")
  public ResponseEntity<HotelResponse> getPlace(@PathVariable Long placeId) {
    HotelResult result = hotelService.findHotelById(placeId);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(HotelResponse.of(result));
  }

}
