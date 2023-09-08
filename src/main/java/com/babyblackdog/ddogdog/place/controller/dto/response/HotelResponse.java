package com.babyblackdog.ddogdog.place.controller.dto.response;

import com.babyblackdog.ddogdog.place.hotel.service.dto.HotelResult;

public record HotelResponse(
    Long id,
    String name,
    String address,
    Long adminId,
    String contact,
    String representative,
    String businessName
) {

  public static HotelResponse of(HotelResult result) {
    return new HotelResponse(
        result.id(),
        result.name(),
        result.address(),
        result.adminId(),
        result.contact(),
        result.representative(),
        result.businessName()
    );
  }

}
