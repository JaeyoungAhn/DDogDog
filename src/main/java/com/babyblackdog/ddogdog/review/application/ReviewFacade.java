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
  private final UserAccessService userAccessService;
  private final PlaceAccessService placeAccessService;

  public ReviewFacade(PlaceAccessService placeAccessService, ReviewService service, UserAccessService userAccessService) {
    this.placeAccessService = placeAccessService;
    this.service = service;
    this.userAccessService = userAccessService;
  }

  public ReviewResult registerReview(Long roomId, String content, Double rating, Long userId) {
    String email = userAccessService.findEmail(userId);
    return service.registerReview(roomId, content, rating, email);
  }

  public ReviewResults findReviewsByUserId(Long userId, Pageable pageable) {
    String email = userAccessService.findEmail(userId);
    return service.findReviewsByEmail(email, pageable);
  }

  public ReviewResults findReviewsByHotelId(Long hotelId, Pageable pageable) {
    List<Long> roomIds = placeAccessService.findRoomIds(hotelId);
    return service.findReviewsByRoomIds(roomIds, pageable);
  }
}
