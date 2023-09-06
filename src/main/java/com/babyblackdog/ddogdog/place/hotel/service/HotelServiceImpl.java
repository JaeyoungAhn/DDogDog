package com.babyblackdog.ddogdog.place.hotel.service;

import static com.babyblackdog.ddogdog.global.error.HotelErrorCode.HOTEL_NOT_FOUND;

import com.babyblackdog.ddogdog.global.exception.HotelException;
import com.babyblackdog.ddogdog.place.hotel.model.Hotel;
import com.babyblackdog.ddogdog.place.hotel.model.vo.Province;
import com.babyblackdog.ddogdog.place.hotel.repository.HotelRepository;
import com.babyblackdog.ddogdog.place.hotel.service.dto.HotelResult;
import com.babyblackdog.ddogdog.place.room.RoomSimpleResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class HotelServiceImpl implements HotelService {

  private final HotelRepository repository;

  public HotelServiceImpl(HotelRepository repository) {
    this.repository = repository;
  }

  @Override
  public Page<HotelResult> findHotelByProvince(Province province, Pageable pageable) {
    Page<Hotel> hotels = repository.findContainsAddress("서울", pageable);
    return hotels.map(HotelResult::of);
  }

  @Override
  public HotelResult findHotelById(Long id) {
    Hotel hotel = repository.findById(id)
        .orElseThrow(() -> new HotelException(HOTEL_NOT_FOUND));
    return HotelResult.of(hotel);
  }

  @Override
  public RoomSimpleResult findRoomInfo(Long placeId, Long roomId) {
    return null;
  }
}
