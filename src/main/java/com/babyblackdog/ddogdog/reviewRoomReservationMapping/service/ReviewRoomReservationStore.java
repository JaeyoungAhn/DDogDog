package com.babyblackdog.ddogdog.reviewRoomReservationMapping.service;


import com.babyblackdog.ddogdog.reviewRoomReservationMapping.domain.ReviewRoomReservation;

public interface ReviewRoomReservationStore {

  /**
   * reviewRoomReservation 을 받아서 저장
   *
   * @param reviewRoomReservation
   */
  void registerReviewRoomReservation(ReviewRoomReservation reviewRoomReservation);

  /**
   * roomId, reservationId 에 해당하는 레코드에 reviewId 관련 정보 추가
   *
   * @param roomId, reservationId, reviewId
   * @return ReviewResult
   */
  void updateReviewRoomReservation(Long roomId, Long reservationId, Long reviewId);
}
