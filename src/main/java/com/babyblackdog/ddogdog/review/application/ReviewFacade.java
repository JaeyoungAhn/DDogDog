package com.babyblackdog.ddogdog.review.application;

import com.babyblackdog.ddogdog.review.service.ReviewService;
import com.babyblackdog.ddogdog.review.service.dto.ReviewResult;
import com.babyblackdog.ddogdog.review.service.dto.ReviewResults;
import com.babyblackdog.ddogdog.reviewRoomReservationMapping.service.ReviewRoomReservationService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReviewFacade {

  private final ReviewService service;

  public ReviewFacade(ReviewService service) {
    this.service = service;
  }

  public ReviewResult registerReview(Long roomId, String content,
      Double rating, Long userId) {
    return service.registerReview(roomId, content, rating, userId);
  }

  public ReviewResult updateReview(Long reviewId, String content, Double rating) {
    return service.updateReview(reviewId, content, rating);
  }

  public ReviewResults findReviewsByUserId(Long userId, Pageable pageable) {
    return service.findReviewsByUserId(userId, pageable);
  }
}
