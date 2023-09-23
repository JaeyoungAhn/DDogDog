package com.babyblackdog.ddogdog.place.controller.dto;

import com.babyblackdog.ddogdog.place.service.dto.HotelResult;

public record HotelResponse(
        Long id,
        String name,
        String address,
        String adminEmail,
        String contact,
        String representative,
        String businessName,
        double ratingScore
) {

    public static HotelResponse of(HotelResult result) {
        return new HotelResponse(
                result.id(),
                result.name(),
                result.address(),
                result.adminEmail(),
                result.contact(),
                result.representative(),
                result.businessName(),
                result.ratingScore()
        );
    }

}
