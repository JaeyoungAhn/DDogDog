package com.babyblackdog.ddogdog.reviewRoomReservationMapping.repository;

import com.babyblackdog.ddogdog.reviewRoomReservationMapping.domain.ReviewRoomReservation;
import com.babyblackdog.ddogdog.reviewRoomReservationMapping.service.ReviewRoomReservationReader;
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
    public Page<ReviewRoomReservation> findReviewRoomReservationsByRoomId(Long roomId, Pageable pageable) {
        return repository.findReviewRoomReservationsByRoomId(roomId, pageable);
    }
}
