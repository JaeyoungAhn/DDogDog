package com.babyblackdog.ddogdog.reviewRoomReservationMapping.repository;

import com.babyblackdog.ddogdog.reviewRoomReservationMapping.domain.ReviewRoomReservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRoomReservationRepository extends
    JpaRepository<ReviewRoomReservation, Long> {

    Page<ReviewRoomReservation> findReviewRoomReservationsByRoomId(Long roomId, Pageable pageable);
}
