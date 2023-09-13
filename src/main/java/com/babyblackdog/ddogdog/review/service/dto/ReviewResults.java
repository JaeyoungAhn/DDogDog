package com.babyblackdog.ddogdog.review.service.dto;

import com.babyblackdog.ddogdog.review.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

public record ReviewResults(Page<ReviewResult> reviewResults) {

  public static ReviewResults of(Page<Review> retrievedReviews) {
    List<ReviewResult> mappedResults = retrievedReviews.getContent()
            .stream()
            .map(review -> new ReviewResult(review.getId(), review.getRoomId(), review.getEmail(), review.getContent(), review.getRating(), review.getCreatedDate()))
        .collect(Collectors.toList());

    Page<ReviewResult> reviewResultPage = new PageImpl<>(
        mappedResults,
        retrievedReviews.getPageable(),
        retrievedReviews.getTotalElements()
    );

    return new ReviewResults(reviewResultPage);
  }
}
