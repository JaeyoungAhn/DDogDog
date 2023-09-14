package com.babyblackdog.ddogdog.place.accessor;

import static com.babyblackdog.ddogdog.global.exception.ErrorCode.HOTEL_NOT_FOUND;

import com.babyblackdog.ddogdog.global.exception.RatingException;
import com.babyblackdog.ddogdog.place.accessor.vo.RoomSimpleResult;
import com.babyblackdog.ddogdog.place.repository.RatingRepository;
import com.babyblackdog.ddogdog.place.service.PlaceService;
import com.babyblackdog.ddogdog.place.service.dto.RoomResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PlaceAccessServiceImpl implements PlaceAccessService {

  private final PlaceService placeService;
  private final RatingRepository ratingRepository;

  public PlaceAccessServiceImpl(PlaceService placeService, RatingRepository ratingRepository) {
    this.placeService = placeService;
    this.ratingRepository = ratingRepository;
  }

  @Override
  public RoomSimpleResult findRoomSimpleInfo(Long roomId) {
    RoomResult roomResult = placeService.findRoom(roomId);
    return RoomSimpleResult.of(roomResult);
  }

  @Override
  @Transactional
  public void addRatingScoreOfHotel(Long hotelId, double ratingScore) {
    ratingRepository.findByHotelId(hotelId)
        .orElseThrow(() -> new RatingException(HOTEL_NOT_FOUND))
        .addRatingScore(ratingScore);
  }

  @Override
  @Transactional
  public void subRatingScoreOfHotel(Long hotelId, double ratingScore) {
    ratingRepository.findByHotelId(hotelId)
        .orElseThrow(() -> new RatingException(HOTEL_NOT_FOUND))
        .subRatingScore(ratingScore);
  }
}
