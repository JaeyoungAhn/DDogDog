package com.babyblackdog.ddogdog.place.reader;

import com.babyblackdog.ddogdog.place.reader.vo.RoomSimpleResult;
import com.babyblackdog.ddogdog.place.room.service.RoomService;
import com.babyblackdog.ddogdog.place.room.service.dto.RoomResult;
import org.springframework.stereotype.Service;

@Service
public class PlaceReaderServiceImpl implements PlaceReaderService {

  private final RoomService roomService;

  public PlaceReaderServiceImpl(RoomService roomService) {
    this.roomService = roomService;
  }

  @Override
  public RoomSimpleResult findRoomSimpleInfo(Long roomId) {
    RoomResult roomResult = roomService.findRoomById(roomId);
    return RoomSimpleResult.of(roomResult);
  }
}
