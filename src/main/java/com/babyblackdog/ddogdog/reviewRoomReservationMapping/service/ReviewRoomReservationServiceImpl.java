package com.babyblackdog.ddogdog.reviewRoomReservationMapping.service;

import com.babyblackdog.ddogdog.review.service.dto.ReviewResult;
import com.babyblackdog.ddogdog.reviewRoomReservationMapping.domain.ReviewRoomReservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReviewRoomReservationServiceImpl implements ReviewRoomReservationService {

  private final ReviewRoomReservationStore store;
  private final ReviewRoomReservationReader reader;

  public ReviewRoomReservationServiceImpl(ReviewRoomReservationStore store, ReviewRoomReservationReader reader) {
    this.store = store;
    this.reader = reader;
  }

  @Override
  public void registerReviewRoomReservation(Long roomId, Long reservationId, Long reviewId) {
    store.registerReviewRoomReservation(new ReviewRoomReservation(roomId, reservationId, reviewId));
  }

  @Override
  public Page<ReviewResult> findReviewsByRoomId(Long roomId, Pageable pageable) {
    Page<ReviewRoomReservation> reviewRoomReservations = reader.findReviewRoomReservationsByRoomId(roomId, pageable);
  }
}
