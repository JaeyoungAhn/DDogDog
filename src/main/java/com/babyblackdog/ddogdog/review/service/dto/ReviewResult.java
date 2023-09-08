package com.babyblackdog.ddogdog.review.service.dto;

import com.babyblackdog.ddogdog.review.domain.Review;
import java.time.LocalDate;

public record ReviewResult(
    Long id,
    String content,
    Double rating,
    LocalDate createdDate
) {

  public static ReviewResult of(Review entity) {
    return new ReviewResult(
        entity.getId(),
        entity.getContent(),
        entity.getRating(),
        entity.getCreatedDate()
    );
  }
}