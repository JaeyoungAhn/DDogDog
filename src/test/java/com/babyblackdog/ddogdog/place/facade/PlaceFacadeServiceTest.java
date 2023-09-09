package com.babyblackdog.ddogdog.place.facade;

import static org.assertj.core.api.Assertions.assertThat;

import com.babyblackdog.ddogdog.place.PlaceTestData;
import com.babyblackdog.ddogdog.place.facade.dto.AddHotelParam;
import com.babyblackdog.ddogdog.place.facade.dto.AddRoomParam;
import com.babyblackdog.ddogdog.place.hotel.repository.HotelRepository;
import com.babyblackdog.ddogdog.place.hotel.service.dto.HotelResult;
import com.babyblackdog.ddogdog.place.room.service.dto.RoomResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class PlaceFacadeServiceTest {

  @Autowired
  private PlaceFacadeService placeService;

  @Autowired
  private HotelRepository hotelRepository;

  @Autowired
  private PlaceTestData placeTestData;

  @Test
  @DisplayName("숙소 추가 요청으로 추가한다.")
  void registerHotel_CreateSuccess() {
    // Given
    AddHotelParam param = placeTestData.getAddHotelParam();

    // When
    HotelResult hotelResult = placeService.registerHotel(param);

    // Then
    assertThat(hotelResult.address()).isEqualTo(param.province().getValue());
    assertThat(hotelResult.name()).isEqualTo(param.hotelName().getValue());
  }

  @Test
  @DisplayName("숙소 제거 요청으로 제거한다.")
  void deleteHotel_DeleteSuccess() {
    // Given
    HotelResult hotelResult = placeService.registerHotel(placeTestData.getAddHotelParam());

    // When
    placeService.deleteHotel(hotelResult.id());

    // Then
    boolean exist = hotelRepository.existsById(hotelResult.id());

    assertThat(exist).isFalse();
  }

  @Test
  @DisplayName("객실 추가 요청으로 추가한다.")
  void registerRoom_CreateSuccess() {
    // Given
    HotelResult hotelResult = placeService.registerHotel(placeTestData.getAddHotelParam());
    AddRoomParam param = placeTestData.bindHotelIdToRoomParam(hotelResult.id());

    // When
    RoomResult roomResult = placeService.registerRoomOfHotel(param);

    // Then
    assertThat(roomResult.roomNumber()).isEqualTo(param.roomNumber().getValue());
    assertThat(roomResult.point()).isEqualTo(param.point().getValue());
  }

}