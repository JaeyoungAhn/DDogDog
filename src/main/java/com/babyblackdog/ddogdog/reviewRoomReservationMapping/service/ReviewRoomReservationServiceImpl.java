package com.babyblackdog.ddogdog.reviewRoomReservationMapping.service;

import com.babyblackdog.ddogdog.reviewRoomReservationMapping.domain.ReviewRoomReservation;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReviewRoomReservationServiceImpl implements ReviewRoomReservationService {

  private final ReviewRoomReservationStore store;
  private final ReviewRoomReservationReader reader;

  public ReviewRoomReservationServiceImpl(ReviewRoomReservationStore store,
      ReviewRoomReservationReader reader) {
    this.store = store;
    this.reader = reader;
  }

  @Override
  public void registerReviewRoomReservation(Long roomId, Long reservationId) {
    store.registerReviewRoomReservation(new ReviewRoomReservation(roomId, reservationId));
  }

  @Override
  public void updateReviewRoomReservation(Long roomId, Long reservationId, Long reviewId) {
    store.updateReviewRoomReservation(roomId, reservationId, reviewId);
  }

  @Override
  public Page<Long> findReviewIdsByRoomId(Long roomId, Pageable pageable) {
    return reader.findReviewIdsByRoomId(roomId, pageable);
  }

  @Override
  public Page<Long> findReviewIdsByRoomIds(List<Long> roomIds, Pageable pageable) {
    return reader.findReviewIdsByRoomIds(roomIds, pageable);
  }
}
