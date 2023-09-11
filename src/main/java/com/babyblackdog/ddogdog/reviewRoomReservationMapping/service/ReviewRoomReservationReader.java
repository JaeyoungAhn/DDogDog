package com.babyblackdog.ddogdog.reviewRoomReservationMapping.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewRoomReservationReader {

  /**
   * roomId, pageable 에 해당하는 reviewId 들을 반환
   *
   * @param roomId, pageable
   */
  Page<Long> findReviewIdsByRoomId(Long roomId, Pageable pageable);

  /**
   * roomIds, pageable 에 해당하는 reviewId 들을 반환
   *
   * @param roomIds, pageable
   */
  Page<Long> findReviewIdsByRoomIds(List<Long> roomIds, Pageable pageable);
}
