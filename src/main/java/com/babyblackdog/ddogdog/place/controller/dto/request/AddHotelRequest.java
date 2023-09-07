package com.babyblackdog.ddogdog.place.controller.dto.request;

import com.babyblackdog.ddogdog.place.hotel.model.vo.BusinessName;
import com.babyblackdog.ddogdog.place.hotel.model.vo.HotelName;
import com.babyblackdog.ddogdog.place.hotel.model.vo.HumanName;
import com.babyblackdog.ddogdog.place.hotel.model.vo.PhoneNumber;
import com.babyblackdog.ddogdog.place.hotel.model.vo.Province;
import com.babyblackdog.ddogdog.place.hotel.service.dto.AddHotelParam;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AddHotelRequest(
    @NotBlank
    String hotelName,
    @NotBlank
    String province,
    @Positive @NotNull
    Long adminId,
    @NotBlank
    String contact,
    @NotBlank
    String representative,
    @NotBlank
    String businessName
) {

  public static AddHotelParam to(AddHotelRequest request) {
    return new AddHotelParam(
        new HotelName(request.hotelName),
        new Province(request.province),
        request.adminId,
        new PhoneNumber(request.contact),
        new HumanName(request.representative),
        new BusinessName(request.businessName)
    );
  }

}
