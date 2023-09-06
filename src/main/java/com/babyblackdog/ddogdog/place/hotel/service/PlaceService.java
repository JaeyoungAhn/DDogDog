package com.babyblackdog.ddogdog.place.hotel.service;

import com.babyblackdog.ddogdog.place.hotel.model.vo.Province;
import com.babyblackdog.ddogdog.place.hotel.service.dto.PlaceResult;
import com.babyblackdog.ddogdog.place.room.RoomBookingResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlaceService {

  /**
   * 지역 이름으로 해당 지역의 모든 숙소 리스트 조회하기
   *
   * @param province
   * @return Page<PlaceResult> -> PlaceResults
   */
  Page<PlaceResult> findPlaceByProvince(Province province, Pageable pageable);

  /**
   * 아이디로 특정 숙소 검색하기
   *
   * @param id
   * @return PlaceResult
   */
  PlaceResult findPlaceById(Long id);

  /**
   * 예약 페이지에 들어올 때, 해당 정보를 받아 예약 페이지를 구성해야 함
   *
   * @return 숙소, 객실 이름과 객실의 가격
   */
  RoomBookingResult findRoomInfo(Long placeId, Long roomId);
}
