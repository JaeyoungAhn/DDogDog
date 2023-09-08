package com.babyblackdog.ddogdog.reviewRoomReservationMapping.service;

public interface ReviewRoomReservationService {

  /**
   * roomId, reservationId, reviewId를 받아 (roomId, reservationId)에 해당하는 reviewId 추가
   *
   * @param reservationId, reservationId, reviewId
   */
  void registerReviewRoomReservation(Long roomId, Long reservationId, Long reviewId);
}
