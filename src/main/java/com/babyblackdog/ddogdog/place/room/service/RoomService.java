package com.babyblackdog.ddogdog.place.room.service;

import com.babyblackdog.ddogdog.common.date.StayPeriod;
import com.babyblackdog.ddogdog.place.room.service.dto.RoomResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoomService {

  RoomResult findRoomById(Long roomId);

  RoomResult findRoomByIdForDuration(Long roomId, StayPeriod stayPeriod);

  Page<RoomResult> findAllRoomsOfHotelForDuration(Long hotelId, StayPeriod stayPeriod,
      Pageable pageable);
}
