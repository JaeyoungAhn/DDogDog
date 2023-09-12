package com.babyblackdog.ddogdog.reviewRoomReservationMapping.application;

import com.babyblackdog.ddogdog.place.room.service.RoomService;
import com.babyblackdog.ddogdog.review.service.ReviewService;
import com.babyblackdog.ddogdog.review.service.dto.ReviewResults;
import com.babyblackdog.ddogdog.reviewRoomReservationMapping.service.ReviewRoomReservationService;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReviewRoomReservationFacadeImpl implements ReviewRoomReservationFacade {

  private final ReviewRoomReservationService service;
  private final ReviewService reviewService;
  private final RoomService roomService;

  public ReviewRoomReservationFacadeImpl(ReviewRoomReservationService service,
      ReviewService reviewService,
      RoomService roomService) {
    this.service = service;
    this.reviewService = reviewService;
    this.roomService = roomService;
  }

//  @Override
//  public ReviewResults findReviewsByHotelId(Long hotelId, Pageable pageable) {
//    List<Long> roomIds = roomService.findRoomIdsByHotelId(hotelId);
//    Page<Long> reviewIds = service.findReviewIdsByRoomIds(roomIds, pageable);
//    return reviewService.findReviewsByReviewIds(reviewIds);
//  }

  @Override
  public ReviewResults findReviewsByHotelId(Long hotelId, Pageable pageable) {
    return null;
  }

  @Override
  public ReviewResults findReviewsByRoomId(Long roomId, Pageable pageable) {
    Page<Long> reviewIds = service.findReviewIdsByRoomId(roomId, pageable);
    return reviewService.findReviewsByReviewIds(reviewIds);
  }
}
