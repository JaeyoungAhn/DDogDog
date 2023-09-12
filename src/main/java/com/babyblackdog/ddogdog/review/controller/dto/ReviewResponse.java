package com.babyblackdog.ddogdog.review.controller.dto;

import com.babyblackdog.ddogdog.review.service.dto.ReviewResult;

import java.time.LocalDate;

public record ReviewResponse(
        Long id,
        Long roomId,
        String content,
        Double rating,
        Long userId,
        LocalDate createdDate
) {

  public static ReviewResponse of(ReviewResult result) {
    return new ReviewResponse(
            result.id(),
            result.roomId(),
            result.content(),
            result.rating(),
            result.userId(),
            result.createdDate()
    );
  }

}
