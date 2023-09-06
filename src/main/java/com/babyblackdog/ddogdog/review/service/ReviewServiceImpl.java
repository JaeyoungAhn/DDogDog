package com.babyblackdog.ddogdog.review.service;

import com.babyblackdog.ddogdog.review.model.Review;
import com.babyblackdog.ddogdog.review.model.vo.Content;
import com.babyblackdog.ddogdog.review.model.vo.Rating;
import com.babyblackdog.ddogdog.review.repository.ReviewRepository;
import com.babyblackdog.ddogdog.review.service.dto.ReviewResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class ReviewServiceImpl implements ReviewService {

  private final ReviewRepository reviewRepository;

  public ReviewServiceImpl(ReviewRepository reviewRepository) {
    this.reviewRepository = reviewRepository;
  }

  @Transactional
  @Override
  public ReviewResult registerReview(Long reservationId, String content, Double rating) {
    Review review = new Review(new Content(content), new Rating(rating));
    Review savedReview = reviewRepository.save(review);
    return ReviewResult.of(savedReview);
  }

}
