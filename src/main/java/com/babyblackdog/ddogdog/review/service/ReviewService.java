package com.babyblackdog.ddogdog.review.service;

import com.babyblackdog.ddogdog.review.service.dto.ReviewResult;

public interface ReviewService {

  /**
   * reservation_id, content, rating 을 받아 리뷰 생성
   *
   * @param reservationId, content, rating
   * @return ReviewResult
   */
  ReviewResult registerReview(Long roomId, Long reservationId, String content, Double rating);
}
