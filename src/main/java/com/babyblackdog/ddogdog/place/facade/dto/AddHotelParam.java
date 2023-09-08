package com.babyblackdog.ddogdog.place.facade.dto;

import com.babyblackdog.ddogdog.place.hotel.model.Hotel;
import com.babyblackdog.ddogdog.place.hotel.model.vo.BusinessName;
import com.babyblackdog.ddogdog.place.hotel.model.vo.HotelName;
import com.babyblackdog.ddogdog.place.hotel.model.vo.HumanName;
import com.babyblackdog.ddogdog.place.hotel.model.vo.PhoneNumber;
import com.babyblackdog.ddogdog.place.hotel.model.vo.Province;
import jakarta.validation.constraints.NotNull;

public record AddHotelParam(
    @NotNull
    HotelName hotelName,
    @NotNull
    Province province,
    @NotNull
    Long adminId,
    @NotNull
    PhoneNumber contact,
    @NotNull
    HumanName representative,
    @NotNull
    BusinessName businessName
) {

  public static Hotel to(AddHotelParam param) {
    return new Hotel(
        param.hotelName,
        param.province,
        param.adminId,
        param.contact,
        param.representative,
        param.businessName
    );
  }
}
