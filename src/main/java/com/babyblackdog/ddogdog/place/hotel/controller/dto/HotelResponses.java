package com.babyblackdog.ddogdog.place.hotel.controller.dto;

import com.babyblackdog.ddogdog.place.hotel.service.dto.HotelResult;
import org.springframework.data.domain.Page;

public record HotelResponses(Page<HotelResponse> hotelResponses) {

  public static HotelResponses of(Page<HotelResult> results) {
    return new HotelResponses(results.map(HotelResponse::of));
  }
}
