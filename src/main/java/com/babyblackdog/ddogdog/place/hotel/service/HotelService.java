package com.babyblackdog.ddogdog.place.hotel.service;

import com.babyblackdog.ddogdog.place.hotel.model.vo.Province;
import com.babyblackdog.ddogdog.place.hotel.service.dto.AddHotelParam;
import com.babyblackdog.ddogdog.place.hotel.service.dto.HotelResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HotelService {

  HotelResult registerHotel(AddHotelParam param);

  Page<HotelResult> findHotelsInProvince(Province province, Pageable pageable);

  HotelResult findHotelById(Long id);

}
