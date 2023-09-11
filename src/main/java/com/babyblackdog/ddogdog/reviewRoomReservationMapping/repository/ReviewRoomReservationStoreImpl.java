package com.babyblackdog.ddogdog.reviewRoomReservationMapping.repository;

import static com.babyblackdog.ddogdog.global.exception.ErrorCode.R3_NOT_FOUND;

import com.babyblackdog.ddogdog.global.exception.ReviewRoomReservationException;
import com.babyblackdog.ddogdog.reviewRoomReservationMapping.domain.ReviewRoomReservation;
import com.babyblackdog.ddogdog.reviewRoomReservationMapping.service.ReviewRoomReservationStore;
import org.springframework.stereotype.Component;

@Component
public class ReviewRoomReservationStoreImpl implements ReviewRoomReservationStore {

  private final ReviewRoomReservationRepository repository;

  public ReviewRoomReservationStoreImpl(ReviewRoomReservationRepository repository) {
    this.repository = repository;
  }

  @Override
  public void registerReviewRoomReservation(ReviewRoomReservation reviewRoomReservation) {
    repository.save(reviewRoomReservation);
  }

  @Override
  public void updateReviewRoomReservation(Long roomId, Long reservationId, Long reviewId) {
    ReviewRoomReservation retrievedR3 = repository.findReviewIdByRoomIdAndReservationId(roomId,
            reservationId)
        .orElseThrow(() -> new ReviewRoomReservationException(R3_NOT_FOUND));
    retrievedR3.setReviewId(reviewId);
  }
}
