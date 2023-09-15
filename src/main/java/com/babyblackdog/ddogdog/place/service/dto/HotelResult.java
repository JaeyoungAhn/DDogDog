package com.babyblackdog.ddogdog.place.service.dto;

import com.babyblackdog.ddogdog.place.model.Hotel;

public record HotelResult(

        Long id,
        String name,
        String address,
        Long adminId,
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
                entity.getAdminId(),
                entity.getContact(),
                entity.getRepresentative(),
                entity.getBusinessName(),
                entity.getRatingScore()
        );
    }


}
