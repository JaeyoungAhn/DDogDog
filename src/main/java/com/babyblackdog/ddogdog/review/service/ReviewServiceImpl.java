package com.babyblackdog.ddogdog.review.service;

import com.babyblackdog.ddogdog.global.exception.ReviewException;
import com.babyblackdog.ddogdog.reservation.model.Reservation;
import com.babyblackdog.ddogdog.reservation.repository.ReservationRepository;
import com.babyblackdog.ddogdog.review.model.Review;
import com.babyblackdog.ddogdog.review.model.vo.Content;
import com.babyblackdog.ddogdog.review.model.vo.Rating;
import com.babyblackdog.ddogdog.review.repository.ReviewRepository;
import com.babyblackdog.ddogdog.review.service.dto.ReviewResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.babyblackdog.ddogdog.global.error.ErrorCode.INVALID_REVIEW;

@Service
public class ReviewServiceImpl implements ReviewService {

  private final ReviewRepository reviewRepository;
  private final ReservationRepository reservationRepository;

  public ReviewServiceImpl(
          ReviewRepository reviewRepository, ReservationRepository reservationRepository) {
    this.reviewRepository = reviewRepository;
    this.reservationRepository = reservationRepository;
  }

  @Override
  public Page<ReviewResult> findReviewsByRoomId(long roomId, Pageable pageable) {
    Page<Review> reviews = reviewRepository.findByRoomId(roomId, pageable);
    return reviews.map(ReviewResult::of);
  }

  @Override
  public ReviewResult addReview(Long reservationId, String content, Double rating) {
    Optional<Reservation> reservation = reservationRepository.findById(reservationId)
            .orElseThrow(() -> new ReviewException(INVALID_REVIEW));
    Review review = new Review(new Content(content), new Rating(rating), reservation.get());
    Review savedReview = reviewRepository.save(review);
    return ReviewResult.of(savedReview);
  }
}
