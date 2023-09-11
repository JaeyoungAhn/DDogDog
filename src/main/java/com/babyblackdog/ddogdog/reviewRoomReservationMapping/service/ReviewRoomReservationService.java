package com.babyblackdog.ddogdog.reviewRoomReservationMapping.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewRoomReservationService {

  /**
   * roomId, reservationId를 받아 레코드를 생성 (reviewId는 초기에 null) order 패키지에 의해 사용
   *
   * @param roomId, reservationId(orderId..?)
   */
  void registerReviewRoomReservation(Long roomId, Long reservationId);

  /**
   * roomId, reservationId, reviewId 를 받아 해당하는 r3 레코드의 reviewId를 추가 기생성된 3r 테이블에 review가 비어있는 것을 이후
   * 추가해주는 과정 review 패키지에 의해 사용
   *
   * @param roomId, reservationId, reviewId
   */
  void updateReviewRoomReservation(Long roomId, Long reservationId, Long reviewId);

  /**
   * roomId, pageable 을 받아 해당하는 Page<ReviewResult> 반환 place 패키지에 의해 사용
   *
   * @param roomId, pageable
   */
  Page<Long> findReviewIdsByRoomId(Long roomId, Pageable pageable);

  /**
   * roomIds, pageable 을 받아 해당하는 Page<Long> 반환 place 패키지에 의해 요청됨
   *
   * @param roomIds, pageable
   */
  Page<Long> findReviewIdsByRoomIds(List<Long> roomIds, Pageable pageable);
}
