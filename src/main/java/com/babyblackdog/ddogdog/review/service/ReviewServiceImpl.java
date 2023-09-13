package com.babyblackdog.ddogdog.review.service;

import com.babyblackdog.ddogdog.review.domain.Review;
import com.babyblackdog.ddogdog.review.domain.vo.Content;
import com.babyblackdog.ddogdog.review.domain.vo.Email;
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
  public ReviewResult registerReview(Long roomId, String content, Double rating, String email) {
    Review review = new Review(roomId, new Content(content), new Rating(rating), new Email(email));
    Review savedReview = store.registerReview(review);
    return ReviewResult.of(savedReview);
  }

  @Transactional
  @Override
  public ReviewResult updateReview(Long reviewId, String content) {
    Review retrievedReview = reader.findReviewById(reviewId);
    retrievedReview.setContent(new Content(content));
    return ReviewResult.of(retrievedReview);
  }

  @Override
  public ReviewResults findReviewsByRoomIds(List<Long> roomIds, Pageable pageable) {
    Page<Review> retrievedReviews = reader.findReviewsByRoomIds(roomIds, pageable);
    return ReviewResults.of(retrievedReviews);
  }

  @Override
  public ReviewResults findReviewsByEmail(String email, Pageable pageable) {
    Page<Review> retrievedReviews = reader.findReviewsByEmail(new Email(email), pageable);
    return ReviewResults.of(retrievedReviews);
  }
}
