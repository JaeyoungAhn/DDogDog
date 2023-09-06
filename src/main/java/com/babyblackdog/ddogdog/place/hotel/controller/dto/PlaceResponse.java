package com.babyblackdog.ddogdog.place.hotel.controller.dto;

import com.babyblackdog.ddogdog.place.hotel.service.dto.PlaceResult;

public record PlaceResponse(
    Long id,
    String name,
    String address,
    Long adminId,
    String contact,
    String representative,
    String businessName
) {

  public static PlaceResponse of(PlaceResult result) {
    return new PlaceResponse(
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
