package com.babyblackdog.ddogdog.review.application;

import com.babyblackdog.ddogdog.place.application.PlaceAccessService;
import com.babyblackdog.ddogdog.review.service.ReviewService;
import com.babyblackdog.ddogdog.review.service.dto.ReviewResults;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewFacade {

  private final ReviewService service;
  private final PlaceAccessService placeAccessService;

  public ReviewFacade(PlaceAccessService placeAccessService, ReviewService service) {
    this.placeAccessService = placeAccessService;
    this.service = service;
  }

  public ReviewResults getReviewsByHotelId(Long hotelId, Pageable pageable) {
    List<Long> roomIds = placeAccessService.findRoomIds(hotelId);
    return service.findReviewsByReviewIds(roomIds, pageable);
  }
}
