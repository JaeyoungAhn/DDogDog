package com.babyblackdog.ddogdog.review.service.dto;

import com.babyblackdog.ddogdog.review.domain.Review;
import java.time.LocalDate;

public record ReviewResult(
        Long id,
        Long roomId,
        String email,
        String content,
        Double rating,
        LocalDate createdDate
) {

    public static ReviewResult of(Review entity) {
        return new ReviewResult(
                entity.getId(),
                entity.getRoomId(),
                entity.getEmail().getValue(),
                entity.getContent(),
                entity.getRating(),
                entity.getCreatedDate()
        );
    }
}