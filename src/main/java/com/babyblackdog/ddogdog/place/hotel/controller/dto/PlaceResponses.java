package com.babyblackdog.ddogdog.place.hotel.controller.dto;

import com.babyblackdog.ddogdog.place.hotel.service.dto.PlaceResult;
import org.springframework.data.domain.Page;

public record PlaceResponses(Page<PlaceResponse> placeResponses) {

  public static PlaceResponses of(Page<PlaceResult> results) {
    return new PlaceResponses(results.map(PlaceResponse::of));
  }
}
