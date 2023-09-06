package com.babyblackdog.ddogdog.place.reader;

import com.babyblackdog.ddogdog.place.reader.vo.RoomSimpleResult;

public interface PlaceReaderService {

  /**
   * 예약 페이지에 들어올 때, 해당 정보를 받아 예약 페이지를 구성해야 함
   *
   * @return 숙소, 객실 이름과 객실의 가격
   */
  RoomSimpleResult findRoomSimpleInfo(Long roomId);

}
