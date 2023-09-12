package com.babyblackdog.ddogdog.reviewRoomReservationMapping.repository;

import com.babyblackdog.ddogdog.reviewRoomReservationMapping.service.ReviewRoomReservationReader;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class ReviewRoomReservationReaderImpl implements ReviewRoomReservationReader {

  private final ReviewRoomReservationRepository repository;

  public ReviewRoomReservationReaderImpl(ReviewRoomReservationRepository repository) {
    this.repository = repository;
  }

  @Override
  public Page<Long> findReviewIdsByRoomId(Long roomId, Pageable pageable) {
    return repository.findReviewIdsByRoomId(roomId, pageable);
  }

  @Override
  public Page<Long> findReviewIdsByRoomIds(List<Long> roomIds, Pageable pageable) {
    return repository.findReviewIdsByRoomIdIn(roomIds, pageable);
  }
}
