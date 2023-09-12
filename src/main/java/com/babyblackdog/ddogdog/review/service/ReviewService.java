package com.babyblackdog.ddogdog.review.service;

import com.babyblackdog.ddogdog.review.service.dto.ReviewResult;
import com.babyblackdog.ddogdog.review.service.dto.ReviewResults;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewService {

  /**
   * roomId, content, rating, userId 을 받아 리뷰 생성
   *
   * @param roomId, content, rating, userId
   * @return ReviewResult
   */
  ReviewResult registerReview(Long roomId, String content, Double rating, Long userId);

  /**
   * reviewId, content, rating 을 받아 리뷰 수정
   *
   * @param reviewId, content, rating
   * @return ReviewResult
   */
  ReviewResult updateReview(Long reviewId, String content, Double rating);

  /**
   * roomIds, pageable 들을 통해 ReviewResults 를 반환
   *
   * @param roomIds, pageable
   * @return ReviewResults
   */
  ReviewResults findReviewsByReviewIds(List<Long> roomIds, Pageable pageable);

  /**
   * userId, pageable 을 통해 Review 들을 반환
   *
   * @param userId, pageable
   * @return ReviewResults
   */
  ReviewResults findReviewsByUserId(Long userId, Pageable pageable);
}
