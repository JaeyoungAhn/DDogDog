package com.babyblackdog.ddogdog.reviewRoomReservationMapping.application;

import com.babyblackdog.ddogdog.review.service.ReviewService;
import com.babyblackdog.ddogdog.review.service.dto.ReviewResult;
import com.babyblackdog.ddogdog.reviewRoomReservationMapping.service.ReviewRoomReservationService;
import org.springframework.stereotype.Service;

@Service
public class ReviewRoomReservationFacade {

  private final ReviewService service;
  private final ReviewRoomReservationService reviewRoomReservationService;

  public ReviewRoomReservationFacade(ReviewService service,
                                     ReviewRoomReservationService reviewRoomReservationService) {
    this.service = service;
    this.reviewRoomReservationService = reviewRoomReservationService;
  }

  public ReviewResult registerReview(Long roomId, Long reservationId, String content, Double rating) {
    ReviewResult savedReview = service.registerReview(roomId, reservationId, content, rating);
    reviewRoomReservationService.registerReviewRoomReservation(roomId, reservationId, savedReview.id());
    return savedReview;
  }

  public ReviewResult updateReview(Long reviewId, String content, Double rating) {
    return service.updateReview(reviewId, content, rating);
  }
}
