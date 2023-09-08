package com.babyblackdog.ddogdog.reviewRoomReservationMapping.service;


import com.babyblackdog.ddogdog.reviewRoomReservationMapping.domain.ReviewRoomReservation;

public interface ReviewRoomReservationStore {

  /**
   * reviewRoomReservation 을 받아서 저장
   *
   * @param reviewRoomReservation
   */
  void registerReviewRoomReservation(ReviewRoomReservation reviewRoomReservation);

}
