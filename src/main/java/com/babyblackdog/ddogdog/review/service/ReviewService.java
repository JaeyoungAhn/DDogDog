package com.babyblackdog.ddogdog.review.service;

import com.babyblackdog.ddogdog.review.service.dto.ReviewResult;
import com.babyblackdog.ddogdog.reviewRoomReservationMapping.domain.ReviewRoomReservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {

  /**
   * reservation_id, content, rating 을 받아 리뷰 생성
   *
   * @param reservationId, content, rating
   * @return ReviewResult
   */
  ReviewResult registerReview(Long roomId, Long reservationId, String content, Double rating);

  /**
   * reviewId, content, rating 을 받아 리뷰 수정
   *
   * @param reviewId, content, rating
   * @return ReviewResult
   */
  ReviewResult updateReview(Long reviewId, String content, Double rating);

  /**
   * userId, pageable을 통해 리뷰들 반환
   *
   * @param userId, pageable
   * @return Page<ReviewResult>
   */
  Page<ReviewResult> findReviewsByUserId(Long userId, Pageable pageable);

  /**
   * Page<ReviewIds> 를 통해 리뷰들 반환
   *
   * @param reviewRoomReservations
   * @return Page<ReviewResult>
   */
  Page<ReviewResult> findReviewsByReviewRoomReservation(Page<ReviewRoomReservation> reviewRoomReservations);
}
