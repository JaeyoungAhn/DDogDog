package com.babyblackdog.ddogdog.place.hotel.service.dto;

import com.babyblackdog.ddogdog.place.hotel.model.Hotel;

public record HotelResult(
    Long id,
    String name,
    String address,
    Long adminId,
    String contact,
    String representative,
    String businessName
) {

  public static HotelResult of(Hotel entity) {
    return new HotelResult(
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
