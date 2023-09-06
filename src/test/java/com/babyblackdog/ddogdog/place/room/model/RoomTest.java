package com.babyblackdog.ddogdog.place.room.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import com.babyblackdog.ddogdog.common.Point;
import com.babyblackdog.ddogdog.global.exception.RoomException;
import com.babyblackdog.ddogdog.place.hotel.model.Hotel;
import com.babyblackdog.ddogdog.place.hotel.model.vo.BusinessName;
import com.babyblackdog.ddogdog.place.hotel.model.vo.HotelName;
import com.babyblackdog.ddogdog.place.hotel.model.vo.HumanName;
import com.babyblackdog.ddogdog.place.hotel.model.vo.PhoneNumber;
import com.babyblackdog.ddogdog.place.hotel.model.vo.Province;
import com.babyblackdog.ddogdog.place.room.model.vo.Occupancy;
import com.babyblackdog.ddogdog.place.room.model.vo.RoomNumber;
import com.babyblackdog.ddogdog.place.room.model.vo.RoomType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RoomTest {

  private Hotel hotel;

  @BeforeEach
  void setUp() {
    hotel = new Hotel(
        new HotelName("신라호텔"),
        new Province("서울"),
        1L,
        new PhoneNumber("01012341234"),
        new HumanName("이부진"),
        new BusinessName("신세계")
    );
  }

  @Test
  @DisplayName("유효한 정보로 객실 엔티티 생성할 수 있다.")
  void createRoomEntity_Success() {
    // Given
    boolean hasBed = true;

    // When
    Room room = new Room(
        hotel,
        RoomType.TWIN,
        "방 설명",
        new Occupancy(2),
        hasBed,
        true,
        false,
        new RoomNumber("404"),
        new Point(100)
    );

    // Then
    assertThat(room.isHasBed()).isEqualTo(hasBed);
  }

  @Test
  @DisplayName("유효하지 않은 정보로 객실 객체 생성할 수 없다.")
  void createRoomEntity_Fail() {
    // Given
    boolean hasBed = true;

    // When & Then
    assertThatThrownBy(() -> new Room(
        hotel,
        RoomType.SINGLE,
        "",
        new Occupancy(-1),
        hasBed,
        false,
        false,
        new RoomNumber(null),
        new Point(-11)
    )).isInstanceOf(RoomException.class);
  }

}