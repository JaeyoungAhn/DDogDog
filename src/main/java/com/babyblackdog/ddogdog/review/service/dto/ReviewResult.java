package com.babyblackdog.ddogdog.review.service.dto;

import com.babyblackdog.ddogdog.review.domain.Review;

import java.time.LocalDate;

public record ReviewResult(
        Long id,
        Long roomId,
        String content,
        Double rating,
        Long userId,
        LocalDate createdDate
) {

  public static ReviewResult of(Review entity) {
    return new ReviewResult(
            entity.getId(),
            entity.getRoomId(),
            entity.getContent(),
            entity.getRating(),
            entity.getUserId(),
            entity.getCreatedDate()
    );
  }
}