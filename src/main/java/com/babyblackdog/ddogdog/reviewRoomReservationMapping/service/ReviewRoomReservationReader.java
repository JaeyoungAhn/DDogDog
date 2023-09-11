package com.babyblackdog.ddogdog.reviewRoomReservationMapping.service;

import com.babyblackdog.ddogdog.reviewRoomReservationMapping.domain.ReviewRoomReservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewRoomReservationReader {

    /**
     * roomId, pageable 에 해당하는 ReviewRoomReservation 들을 반환
     *
     * @param roomId, pageable
     */
    Page<ReviewRoomReservation> findReviewRoomReservationsByRoomId(Long roomId, Pageable pageable);
}
