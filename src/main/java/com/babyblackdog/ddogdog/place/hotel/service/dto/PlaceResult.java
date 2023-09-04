package com.babyblackdog.ddogdog.place.hotel.service.dto;

import com.babyblackdog.ddogdog.place.hotel.model.Place;

public record PlaceResult(
    Long id,
    String name,
    String address,
    Long adminId,
    String contact,
    String representative,
    String businessName
) {

  public static PlaceResult of(Place entity) {
    return new PlaceResult(
        entity.getId(),
        entity.getName(),
        entity.getAddressValue(),
        entity.getAdminId(),
        entity.getContact(),
        entity.getRepresentative(),
        entity.getBusinessName()
    );
  }

}
