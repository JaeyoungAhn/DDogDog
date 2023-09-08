package com.babyblackdog.ddogdog.review.service;

import com.babyblackdog.ddogdog.review.domain.Review;
import com.babyblackdog.ddogdog.review.domain.vo.Content;
import com.babyblackdog.ddogdog.review.domain.vo.Rating;
import com.babyblackdog.ddogdog.review.service.dto.ReviewResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class ReviewServiceImpl implements ReviewService {

  private final ReviewStore store;

  public ReviewServiceImpl(ReviewStore store) {
    this.store = store;
  }

  @Transactional
  @Override
  public ReviewResult registerReview(Long roomId, Long reservationId, String content, Double rating) {
    Review review = new Review(new Content(content), new Rating(rating));
    Review savedReview = store.registerReview(review);
    return ReviewResult.of(savedReview);
  }

}
