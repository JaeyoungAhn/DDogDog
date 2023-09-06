package com.babyblackdog.ddogdog.place.room.service;

import com.babyblackdog.ddogdog.place.room.service.dto.RoomResult;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class RoomServiceImpl implements
    RoomService {

  @Override
  public RoomResult findRoomById(Long roomId) {
    return null;
  }

  @Override
  public Page<RoomResult> findAllRoomsOfHotelForDuration(Long hotelId, LocalDate checkIn,
      LocalDate checkOut) {
    return null;
  }

  @Override
  public RoomResult findRoomByHotelId(Long roomId, LocalDate checkIn, LocalDate checkOut) {
    return null;
  }
}
