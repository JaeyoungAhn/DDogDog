package com.babyblackdog.ddogdog.review.service;

import com.babyblackdog.ddogdog.review.model.Review;
import com.babyblackdog.ddogdog.review.repository.ReviewRepository;
import com.babyblackdog.ddogdog.review.service.dto.ReviewResult;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {

  private final ReviewRepository reviewRepository;

  public ReviewServiceImpl(
      ReviewRepository reviewRepository) {
    this.reviewRepository = reviewRepository;
  }

  @Override
  public Page<Review> findReviewsByRoomId(long roomId) {
    return null;
  }

  @Override
  public ReviewResult addReview(Long reservationId, String content, Double rating) {
    return null;
  }
}
