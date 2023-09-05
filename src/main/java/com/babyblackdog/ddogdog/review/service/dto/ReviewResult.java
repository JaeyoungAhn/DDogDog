package com.babyblackdog.ddogdog.review.service.dto;


import com.babyblackdog.ddogdog.place.PlaceResult;
import com.babyblackdog.ddogdog.reservation.model.Reservation;
import com.babyblackdog.ddogdog.review.model.Review;
import com.babyblackdog.ddogdog.review.model.vo.Content;
import com.babyblackdog.ddogdog.review.model.vo.Rating;

import java.time.LocalDateTime;

public record ReviewResult(
        Long id,
        String content,
        Double rating,
        LocalDateTime createdDate,
        Long reservation
) {

    public static ReviewResult of(Review entity) {
        return new ReviewResult(
                entity.getId(),
                entity.getContent(),
                entity.getRating(),
                entity.getCreatedDate(),
                entity.getReservation()
        );
    }

}