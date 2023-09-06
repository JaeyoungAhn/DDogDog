package com.babyblackdog.ddogdog.place.hotel.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.babyblackdog.ddogdog.place.hotel.controller.dto.PlaceResponse;
import com.babyblackdog.ddogdog.place.hotel.controller.dto.PlaceResponses;
import com.babyblackdog.ddogdog.place.hotel.model.vo.Province;
import com.babyblackdog.ddogdog.place.hotel.service.PlaceService;
import com.babyblackdog.ddogdog.place.hotel.service.dto.PlaceResult;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/places", produces = APPLICATION_JSON_VALUE)
public class PlaceRestController {

  private final PlaceService placeService;

  public PlaceRestController(PlaceService placeService) {
    this.placeService = placeService;
  }

  @GetMapping
  public ResponseEntity<PlaceResponses> getPlacesByProvince(@RequestParam String province,
      Pageable pageable) {
    Page<PlaceResult> result = placeService.findPlaceByProvince(new Province(province), pageable);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(PlaceResponses.of(result));
  }

  @GetMapping("/{placeId}")
  public ResponseEntity<PlaceResponse> getPlace(@PathVariable Long placeId) {
    PlaceResult result = placeService.findPlaceById(placeId);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(PlaceResponse.of(result));
  }

}
