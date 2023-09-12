package com.babyblackdog.ddogdog.reviewRoomReservationMapping.repository;

import com.babyblackdog.ddogdog.reviewRoomReservationMapping.domain.ReviewRoomReservation;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRoomReservationRepository extends
    JpaRepository<ReviewRoomReservation, Long> {

  Page<Long> findReviewIdsByRoomId(Long roomId, Pageable pageable);

  Page<Long> findReviewIdsByRoomIdIn(List<Long> roomIds, Pageable pageable);

  Optional<ReviewRoomReservation> findReviewIdByRoomIdAndReservationId(Long roomId,
      Long reservationId);
}
