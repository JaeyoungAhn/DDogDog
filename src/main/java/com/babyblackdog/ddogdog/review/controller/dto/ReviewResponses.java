package com.babyblackdog.ddogdog.review.controller.dto;

import com.babyblackdog.ddogdog.review.service.dto.ReviewResult;
import com.babyblackdog.ddogdog.review.service.dto.ReviewResults;
import java.util.List;
import java.util.stream.Collectors;

public record ReviewResponses(List<ReviewResponse> reviewResponses) {

  public static ReviewResponses of(ReviewResults reviewsResult) {
    List<ReviewResponse> convertedResponses = reviewsResult.reviewResults().stream()
        .map(ReviewResponses::convertToReviewResponse)
        .collect(Collectors.toList());

    return new ReviewResponses(convertedResponses);
  }

  private static ReviewResponse convertToReviewResponse(ReviewResult reviewResult) {
    return new ReviewResponse(
        reviewResult.id(),
        reviewResult.content(),
        reviewResult.rating(),
        reviewResult.userId(),
        reviewResult.createdDate()
    );
  }
}
