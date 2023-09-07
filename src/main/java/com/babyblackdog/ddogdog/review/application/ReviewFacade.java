package com.babyblackdog.ddogdog.review.application;

import com.babyblackdog.ddogdog.review.service.ReviewService;
import com.babyblackdog.ddogdog.review.service.dto.ReviewResult;
import com.babyblackdog.ddogdog.reviewRoomReservationMapping.service.ReviewRoomReservationService;
import org.springframework.stereotype.Service;

@Service
public class ReviewFacade {

    private final ReviewService service;
    private final ReviewRoomReservationService reviewRoomReservationService;

    public ReviewFacade(ReviewService service, ReviewRoomReservationService reviewRoomReservationService) {
        this.service = service;
        this.reviewRoomReservationService = reviewRoomReservationService;
    }

    public ReviewResult registerReview(Long roomId, Long reservationId, String content, Double rating) {
        ReviewResult savedReview = service.registerReview(roomId, reservationId, content, rating);
        reviewRoomReservationService.registerReviewRoomReservation(roomId, reservationId, savedReview.id());
        return savedReview;
    }
}
