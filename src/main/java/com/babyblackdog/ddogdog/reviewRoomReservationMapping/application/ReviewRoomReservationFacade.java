package com.babyblackdog.ddogdog.reviewRoomReservationMapping.application;

import com.babyblackdog.ddogdog.review.service.dto.ReviewResults;
import org.springframework.data.domain.Pageable;

public interface ReviewRoomReservationFacade {

  /**
   * hotelId, pageable 을 받아 해당하는 ReviewResults 반환 place 패키지에 의해 사용
   *
   * @param hotelId, pageable
   * @return ReviewResults
   */
  ReviewResults findReviewsByHotelId(Long hotelId, Pageable pageable);

  /**
   * roomId, pageable 을 받아 해당하는 ReviewResults 반환 place 패키지에 의해 사용
   *
   * @param roomId, pageable
   * @return ReviewResults
   */
  ReviewResults findReviewsByRoomId(Long roomId, Pageable pageable);
}
