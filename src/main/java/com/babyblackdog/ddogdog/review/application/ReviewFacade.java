package com.babyblackdog.ddogdog.review.application;

import com.babyblackdog.ddogdog.place.application.PlaceAccessService;
import com.babyblackdog.ddogdog.review.service.ReviewService;
import com.babyblackdog.ddogdog.review.service.dto.ReviewResult;
import com.babyblackdog.ddogdog.review.service.dto.ReviewResults;
import com.babyblackdog.ddogdog.user.service.UserAccessService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewFacade {

  private final ReviewService service;
  private final PlaceAccessService placeAccessService;

  public ReviewFacade(ReviewService service, PlaceAccessService placeAccessService) {
    this.service = service;
    this.placeAccessService = placeAccessService;
  }

  public ReviewResult registerReview(Long roomId, String content, Double rating, String email) {
    placeAccessService.addRating(roomId, rating);
    return service.registerReview(roomId, content, rating, email);
  }

  public ReviewResult updateReview(Long reviewId, String content) {
    return service.updateReview(reviewId, content);
  }

  public ReviewResults findReviewsByEmail(String email, Pageable pageable) {
    return service.findReviewsByEmail(email, pageable);
  }

  public ReviewResults findReviewsByHotelId(Long hotelId, Pageable pageable) {
    List<Long> roomIds = placeAccessService.findRoomIds(hotelId);
    return service.findReviewsByRoomIds(roomIds, pageable);
  }
}
