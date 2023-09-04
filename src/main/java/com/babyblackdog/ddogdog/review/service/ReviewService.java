package com.babyblackdog.ddogdog.review.service;

import com.babyblackdog.ddogdog.review.model.Review;
import com.babyblackdog.ddogdog.review.service.dto.ReviewResult;
import org.springframework.data.domain.Page;

public interface ReviewService {

  /**
   * 특정 roomId 에 따른 모든 리뷰 반환
   *
   * @param roomId
   * @return Page<Review>
   */
  Page<Review> findReviewsByRoomId(long roomId);

  /**
   * reservation_id, content, rating 을 받아 리뷰 생성
   *
   * @param reservationId, content, rating
   * @return ReviewResult
   */
  ReviewResult addReview(Long reservationId, String content, Double rating);
}
