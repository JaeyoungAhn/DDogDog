package com.babyblackdog.ddogdog.place.room.service;

import com.babyblackdog.ddogdog.place.room.service.dto.RoomResult;
import java.time.LocalDate;
import org.springframework.data.domain.Page;

public interface RoomService {

  /**
   * 숙소 아이디, 객실 아이디로 특정 객실 정보를 반환한다.
   *
   * @param roomId
   * @return RoomSimpleResult
   */
  RoomResult findRoomById(Long roomId);

  /**
   * 특정 기간 동안 특정 숙소 아이디에 대한 모든 객실 리스트를 조회한다
   *
   * @param hotelId
   * @param checkIn
   * @param checkOut
   * @return Page<RoomResult>
   */
  Page<RoomResult> findAllRoomsOfHotelForDuration(Long hotelId, LocalDate checkIn,
      LocalDate checkOut);

  /**
   * 특정 기간 동안 객실 아이디로 특정 객실 검색하기
   *
   * @param roomId
   * @return RoomResult
   */
  RoomResult findRoomByHotelId(Long roomId, LocalDate checkIn, LocalDate checkOut);
}
