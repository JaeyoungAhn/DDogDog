package com.babyblackdog.ddogdog.place.hotel.service;

import com.babyblackdog.ddogdog.place.hotel.model.vo.Province;
import com.babyblackdog.ddogdog.place.hotel.service.dto.HotelResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HotelService {

  /**
   * 지역 이름으로 해당 지역의 모든 숙소 리스트 조회하기
   *
   * @param province
   * @return Page<HotelResult> -> PlaceResults
   */
  Page<HotelResult> findHotelByProvince(Province province, Pageable pageable);

  /**
   * 아이디로 특정 숙소 검색하기
   *
   * @param id
   * @return HotelResult
   */
  HotelResult findHotelById(Long id);

}
