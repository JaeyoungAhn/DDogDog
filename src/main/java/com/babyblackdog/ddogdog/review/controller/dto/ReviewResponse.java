package com.babyblackdog.ddogdog.review.controller.dto;

import com.babyblackdog.ddogdog.review.service.dto.ReviewResult;
import java.time.LocalDate;

public record ReviewResponse(
    Long id,
    String content,
    Double rating,
    LocalDate createdDate
) {

  public static ReviewResponse of(ReviewResult result) {
    return new ReviewResponse(
        result.id(),
        result.content(),
        result.rating(),
        result.createdDate()
    );
  }

}
