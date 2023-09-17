package com.babyblackdog.ddogdog.place.service;

import static com.babyblackdog.ddogdog.global.exception.ErrorCode.HOTEL_NOT_FOUND;
import static com.babyblackdog.ddogdog.global.exception.ErrorCode.ROOM_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.babyblackdog.ddogdog.global.exception.ErrorCode;
import com.babyblackdog.ddogdog.global.exception.HotelException;
import com.babyblackdog.ddogdog.global.exception.RoomException;
import com.babyblackdog.ddogdog.place.PlaceTestData;
import com.babyblackdog.ddogdog.place.model.vo.Province;
import com.babyblackdog.ddogdog.place.service.dto.AddHotelParam;
import com.babyblackdog.ddogdog.place.service.dto.AddRoomParam;
import com.babyblackdog.ddogdog.place.service.dto.HotelResult;
import com.babyblackdog.ddogdog.place.service.dto.RoomResult;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@SpringBootTest
@Transactional
class PlaceServiceImplTest {

    @Autowired
    private PlaceService placeService;

    @Autowired
    private PlaceTestData placeTestData;

    @Test
    @DisplayName("숙소 추가 요청으로 추가한다.")
    void registerHotel_CreateSuccess() {
        // Given
        AddHotelParam param = placeTestData.addHotelParam();

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
        HotelResult hotelResult = placeService.registerHotel(placeTestData.addHotelParam());

        // When
        placeService.deleteHotel(hotelResult.id());

        // Then
        boolean exists = placeService.existsHotel(hotelResult.id());

        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("객실 추가 요청으로 추가한다.")
    void registerRoom_CreateSuccess() {
        // Given
        HotelResult hotelResult = placeService.registerHotel(placeTestData.addHotelParam());
        AddRoomParam param = placeTestData.addRoomParam(hotelResult.id());

        // When
        RoomResult roomResult = placeService.registerRoomOfHotel(param);

        // Then
        assertThat(roomResult.roomNumber()).isEqualTo(param.roomNumber().getValue());
        assertThat(roomResult.point()).isEqualTo(param.point().getValue());
    }

    @Test
    @DisplayName("객실 제거 요청으로 제거한다.")
    void deleteRoom_DeleteSuccess() {
        // Given
        HotelResult hotelResult = placeService.registerHotel(placeTestData.addHotelParam());
        AddRoomParam param = placeTestData.addRoomParam(hotelResult.id());
        RoomResult roomResult = placeService.registerRoomOfHotel(param);

        // When
        placeService.deleteRoom(roomResult.id());

        // Then
        assertThatThrownBy(() -> placeService.findRoom(roomResult.id()))
                .isInstanceOf(RoomException.class)
                .hasMessage(ROOM_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("유효한 지역 이름으로 숙소를 조회하면 성공한다.")
    void findHotelsInProvince_ReadSuccess() {
        // Given
        HotelResult hotelResult = placeService.registerHotel(placeTestData.addHotelParam());

        // When
        Page<HotelResult> result = placeService.findHotelsInProvince(
                new Province(hotelResult.address()), PageRequest.of(0, 2));

        // Then
        assertThat(result)
                .isNotNull()
                .isNotEmpty();
        assertThat(result.getContent()).hasSize(1);
    }

    @Test
    @DisplayName("유효하지 않은 지역 이름으로 숙소를 조회하면 실패한다.")
    void findHotelsInProvince_ReadException() {
        // Given
        Province invalidProvince = new Province("평양");

        // When
        Page<HotelResult> result = placeService.findHotelsInProvince(
                invalidProvince, PageRequest.of(0, 2));

        // Then
        assertThat(result)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("유효한 숙소 아이디를 이용해 숙소를 조회하면 성공한다.")
    void findHotel_ReadSuccess() {
        // Given
        HotelResult savedHotel = placeService.registerHotel(placeTestData.addHotelParam());

        // When
        HotelResult result = placeService.findHotel(savedHotel.id());

        // Then
        assertThat(result).isEqualTo(savedHotel);
    }

    @Test
    @DisplayName("유효하지 않은 숙소 아이디를 이용해 숙소를 조회하면 실패한다.")
    void findHotel_ReadException() {
        // Given
        Long invalidId = 1_000L;

        // When & Then
        assertThatThrownBy(() -> placeService.findHotel(invalidId))
                .isInstanceOf(HotelException.class)
                .hasMessage(HOTEL_NOT_FOUND.toString());
    }

    @Test
    @DisplayName("존재하는 객실을 객실 아이디로 조회할 수 있다.")
    void findRoom_Success() {
        // Given
        HotelResult hotelResult = placeService.registerHotel(placeTestData.addHotelParam());
        RoomResult savedRoom = placeService.registerRoomOfHotel(placeTestData.addRoomParam(hotelResult.id()));

        // When
        RoomResult roomResult = placeService.findRoom(savedRoom.id());

        // Then
        assertThat(roomResult).isEqualTo(savedRoom);
    }

    @Test
    @DisplayName("존재하지 않는 객실을 객실 아이디로 조회할 수 없다.")
    void findRoom_Exception() {
        // Given
        Long invalidId = 1_000L;

        // When & Then
        assertThatThrownBy(() -> placeService.findRoom(invalidId))
                .isInstanceOf(RoomException.class)
                .hasMessage(ErrorCode.ROOM_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("호텔의 모든 객실을 조회한다.")
    void findRoomsOfHotel_Success() {
        // Given
        HotelResult savedHotel = placeService.registerHotel(placeTestData.addHotelParam());
        placeService.registerRoomOfHotel(placeTestData.addRoomParam(savedHotel.id()));

        // When
        Page<RoomResult> roomResults = placeService.findRoomsOfHotel(
                savedHotel.id(), PageRequest.of(0, 2));

        // Then
        assertThat(roomResults)
                .isNotNull()
                .isNotEmpty();
        assertThat(roomResults.getContent()).hasSize(1);
    }

}