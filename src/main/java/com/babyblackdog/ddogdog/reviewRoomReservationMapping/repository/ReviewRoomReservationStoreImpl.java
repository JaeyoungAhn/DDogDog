package com.babyblackdog.ddogdog.reviewRoomReservationMapping.repository;

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
}
