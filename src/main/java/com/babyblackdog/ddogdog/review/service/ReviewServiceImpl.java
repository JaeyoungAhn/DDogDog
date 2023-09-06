package com.babyblackdog.ddogdog.review.service;

import com.babyblackdog.ddogdog.reservation.model.Reservation;
import com.babyblackdog.ddogdog.review.model.Review;
import com.babyblackdog.ddogdog.review.model.vo.Content;
import com.babyblackdog.ddogdog.review.model.vo.Rating;
import com.babyblackdog.ddogdog.review.repository.ReviewRepository;
import com.babyblackdog.ddogdog.review.service.dto.ReviewResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class ReviewServiceImpl implements ReviewService {

  private final ReviewRepository reviewRepository;
  private final ReservationReader reservationReader;

  public ReviewServiceImpl(ReviewRepository reviewRepository) {
    this.reviewRepository = reviewRepository;
  }

  @Override
  public Page<ReviewResult> findReviewsByRoomId(long roomId, Pageable pageable) {
    Page<Review> reviews = reviewRepository.findByRoomId(roomId, pageable);
    return reviews.map(ReviewResult::of);
  }

  @Transactional
  @Override
  public ReviewResult registerReview(Long reservationId, String content, Double rating) {
    Reservation reservation = reservationReader.findById(reservationId);
    Review review = new Review(new Content(content), new Rating(rating), reservation);
    Review savedReview = reviewRepository.save(review);
    return ReviewResult.of(savedReview);
  }
}
