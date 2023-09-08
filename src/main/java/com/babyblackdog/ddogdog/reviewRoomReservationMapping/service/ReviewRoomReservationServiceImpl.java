package com.babyblackdog.ddogdog.reviewRoomReservationMapping.service;

import com.babyblackdog.ddogdog.reviewRoomReservationMapping.domain.ReviewRoomReservation;
import org.springframework.stereotype.Service;

@Service
public class ReviewRoomReservationServiceImpl implements ReviewRoomReservationService {

  private final ReviewRoomReservationStore store;

  public ReviewRoomReservationServiceImpl(ReviewRoomReservationStore store) {
    this.store = store;
  }

  @Override
  public void registerReviewRoomReservation(Long roomId, Long reservationId, Long reviewId) {
    store.registerReviewRoomReservation(new ReviewRoomReservation(roomId, reservationId, reviewId));
  }
}
