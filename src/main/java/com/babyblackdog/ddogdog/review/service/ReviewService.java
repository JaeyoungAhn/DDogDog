package com.babyblackdog.ddogdog.review.service;

import com.babyblackdog.ddogdog.review.service.dto.ReviewResult;
import com.babyblackdog.ddogdog.review.service.dto.ReviewResults;
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
   * reviewId 들을 통해 Review 들을 반환
   *
   * @param reviewIds
   * @return ReviewResults
   */
  ReviewResults findReviewsByReviewIds(Page<Long> reviewIds);

  /**
   * userId, pageable 을 통해 Review 들을 반환
   *
   * @param userId, pageable
   * @return ReviewResults
   */
  ReviewResults findReviewsByUserId(Long userId, Pageable pageable);
}
