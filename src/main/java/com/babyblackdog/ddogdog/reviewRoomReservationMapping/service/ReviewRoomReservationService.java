package com.babyblackdog.ddogdog.reviewRoomReservationMapping.service;

import com.babyblackdog.ddogdog.review.service.dto.ReviewResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewRoomReservationService {

  /**
   * roomId, reservationId, reviewId를 받아 (roomId, reservationId)에 해당하는 reviewId 추가
   *
   * @param reservationId, reservationId, reviewId
   */
  void registerReviewRoomReservation(Long roomId, Long reservationId, Long reviewId);

  /**
   * roomId, pageable 을 받아 해당하는 Page<ReviewResult> 반환
   *
   * @param roomId, pageable
   */
  Page<ReviewResult> findReviewsByRoomId(Long roomId, Pageable pageable);

}
