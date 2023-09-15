package com.babyblackdog.ddogdog.place.accessor;

import com.babyblackdog.ddogdog.place.accessor.vo.RoomSimpleResult;
import com.babyblackdog.ddogdog.place.service.PlaceService;
import com.babyblackdog.ddogdog.place.service.dto.RoomResult;
import org.springframework.stereotype.Service;

@Service
public class PlaceAccessServiceImpl implements PlaceAccessService {

  private final PlaceService placeService;

  public PlaceAccessServiceImpl(PlaceService placeService) {
    this.placeService = placeService;
  }

  @Override
  public RoomSimpleResult findRoomSimpleInfo(Long roomId) {
    RoomResult roomResult = placeService.findRoom(roomId);
    return RoomSimpleResult.of(roomResult);
  }
}
