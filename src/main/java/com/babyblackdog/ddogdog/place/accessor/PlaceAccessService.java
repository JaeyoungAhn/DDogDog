package com.babyblackdog.ddogdog.place.accessor;

import com.babyblackdog.ddogdog.place.accessor.vo.RoomSimpleResult;

public interface PlaceAccessService {

  /**
   * 예약 페이지에 들어올 때, 해당 정보를 받아 예약 페이지를 구성해야 함
   *
   * @return 숙소, 객실 이름과 객실의 가격
   */
  RoomSimpleResult findRoomSimpleInfo(Long roomId);

  /**
   * 리뷰 추가 시 해당 리뷰 점수를 평균 별점에 갱신
   *
   * @param hotelId
   * @param ratingScore
   */
  void addRatingScoreOfHotel(Long hotelId, double ratingScore);

  /**
   * 리뷰 제거 시 해당 리뷰 점수를 평균 별점에 갱신
   *
   * @param hotelId
   * @param ratingScore
   */
  void subRatingScoreOfHotel(Long hotelId, double ratingScore);

}
