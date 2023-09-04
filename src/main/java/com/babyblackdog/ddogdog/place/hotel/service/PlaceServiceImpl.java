package com.babyblackdog.ddogdog.place.hotel.service;

import static com.babyblackdog.ddogdog.global.error.ErrorCode.PLACE_NOT_FOUND;

import com.babyblackdog.ddogdog.global.exception.PlaceException;
import com.babyblackdog.ddogdog.place.hotel.model.Place;
import com.babyblackdog.ddogdog.place.hotel.model.vo.Province;
import com.babyblackdog.ddogdog.place.hotel.repository.PlaceRepository;
import com.babyblackdog.ddogdog.place.room.RoomBookingResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PlaceServiceImpl implements PlaceService {

  private final PlaceRepository repository;

  public PlaceServiceImpl(PlaceRepository repository) {
    this.repository = repository;
  }

  @Override
  public Page<PlaceResult> findPlaceByProvince(Province province, Pageable pageable) {
    Page<Place> places = repository.findPlaceByAddressContains("서울", pageable);
    return places.map(PlaceResult::of);
  }

  @Override
  public PlaceResult findPlaceById(Long id) {
    Place place = repository.findById(id)
        .orElseThrow(() -> new PlaceException(PLACE_NOT_FOUND));
    return PlaceResult.of(place);
  }

  @Override
  public RoomBookingResult findRoomInfo(Long placeId, Long roomId) {
    return null;
  }
}
