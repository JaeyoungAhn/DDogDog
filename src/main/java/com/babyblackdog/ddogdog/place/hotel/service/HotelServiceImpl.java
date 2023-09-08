package com.babyblackdog.ddogdog.place.hotel.service;

import static com.babyblackdog.ddogdog.global.exception.ErrorCode.HOTEL_NOT_FOUND;

import com.babyblackdog.ddogdog.global.exception.HotelException;
import com.babyblackdog.ddogdog.place.hotel.model.Hotel;
import com.babyblackdog.ddogdog.place.hotel.model.vo.Province;
import com.babyblackdog.ddogdog.place.hotel.repository.HotelRepository;
import com.babyblackdog.ddogdog.place.hotel.service.dto.AddHotelParam;
import com.babyblackdog.ddogdog.place.hotel.service.dto.HotelResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class HotelServiceImpl implements HotelService {

  private final HotelRepository repository;

  public HotelServiceImpl(HotelRepository repository) {
    this.repository = repository;
  }

  @Override
  @Transactional
  public HotelResult registerHotel(AddHotelParam param) {
    Hotel hotel = repository.save(AddHotelParam.to(param));
    return HotelResult.of(hotel);
  }

  @Override
  public Page<HotelResult> findHotelsInProvince(Province province, Pageable pageable) {
    Page<Hotel> hotels = repository.findContainsAddress("서울", pageable);
    return hotels.map(HotelResult::of);
  }

  @Override
  public HotelResult findHotelById(Long id) {
    Hotel hotel = repository.findById(id)
        .orElseThrow(() -> new HotelException(HOTEL_NOT_FOUND));
    return HotelResult.of(hotel);
  }

}
