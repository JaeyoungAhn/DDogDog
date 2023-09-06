package com.babyblackdog.ddogdog.review.service;

import com.babyblackdog.ddogdog.review.model.Review;
import com.babyblackdog.ddogdog.review.service.dto.ReviewResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {

  /**
   * 특정 roomId 에 따른 모든 리뷰 반환
   *
   * @param roomId
   * @return Page<Review>
   */
  Page<ReviewResult> findReviewsByRoomId(long roomId, Pageable pageable);

  /**
   * reservation_id, content, rating 을 받아 리뷰 생성
   *
   * @param reservationId, content, rating
   * @return ReviewResult
   */
  ReviewResult registerReview(Long reservationId, String content, Double rating);
}
