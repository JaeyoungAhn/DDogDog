package com.babyblackdog.ddogdog.review.service;

import com.babyblackdog.ddogdog.review.domain.Review;
import com.babyblackdog.ddogdog.review.domain.vo.Content;
import com.babyblackdog.ddogdog.review.domain.vo.Rating;
import com.babyblackdog.ddogdog.review.service.dto.ReviewResult;
import com.babyblackdog.ddogdog.review.service.dto.ReviewResults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class ReviewServiceImpl implements ReviewService {

  private final ReviewStore store;
  private final ReviewReader reader;

  public ReviewServiceImpl(ReviewStore store, ReviewReader reader) {
    this.store = store;
    this.reader = reader;
  }

  @Transactional
  @Override
  public ReviewResult registerReview(Long roomId, String content, Double rating, Long userId) {
    Review review = new Review(roomId, new Content(content), new Rating(rating), userId);
    Review savedReview = store.registerReview(review);
    return ReviewResult.of(savedReview);
  }

  @Transactional
  @Override
  public ReviewResult updateReview(Long reviewId, String content, Double rating) {
    Review retrievedReview = reader.findReviewById(reviewId);
    retrievedReview.setContent(new Content(content));
    retrievedReview.setRating(new Rating(rating));
    return ReviewResult.of(retrievedReview);
  }

  @Override
  public ReviewResults findReviewsByReviewIds(List<Long> reviewIds, Pageable pageable) {
    Page<Review> retrievedReviews = reader.findReviewsByReviewIds(reviewIds, pageable);
    return ReviewResults.of(retrievedReviews);
  }

  @Override
  public ReviewResults findReviewsByUserId(Long userId, Pageable pageable) {
    Page<Review> retrievedReviews = reader.findReviewsByUserId(userId, pageable);
    return ReviewResults.of(retrievedReviews);
  }
}
