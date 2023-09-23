package com.babyblackdog.ddogdog.place.service.dto;

import com.babyblackdog.ddogdog.place.model.Hotel;

public record HotelResult(

        Long id,
        String name,
        String address,
        String adminEmail,
        String contact,
        String representative,
        String businessName,
        double ratingScore
) {

    public static HotelResult of(Hotel entity) {
        return new HotelResult(
                entity.getId(),
                entity.getName(),
                entity.getAddressValue(),
                entity.getEmailAddress(),
                entity.getContact(),
                entity.getRepresentative(),
                entity.getBusinessName(),
                entity.getRatingScore()
        );
    }


}
