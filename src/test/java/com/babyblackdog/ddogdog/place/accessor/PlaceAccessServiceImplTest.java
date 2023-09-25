package com.babyblackdog.ddogdog.place.accessor;

import static org.assertj.core.api.Assertions.assertThat;

import com.babyblackdog.ddogdog.place.PlaceTestData;
import com.babyblackdog.ddogdog.place.accessor.vo.RoomSimpleResult;
import com.babyblackdog.ddogdog.place.service.PlaceService;
import com.babyblackdog.ddogdog.place.service.dto.HotelResult;
import com.babyblackdog.ddogdog.place.service.dto.RoomResult;
import jakarta.transaction.Transactional;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {"spring.profiles.active=test"})
@Transactional
class PlaceAccessServiceImplTest {

    @Autowired
    private PlaceAccessService placeAccessService;

    @Autowired
    private PlaceTestData placeTestData;
    @Autowired
    private PlaceService placeService;

    @Test
    @DisplayName("객실 아이디로 예약 정보에 표출할 간단한 객실 정보를 조회한다.")
    void findRoomSimpleInfo_Success() {
        // Given
        HotelResult hotelResult = placeService.registerHotel(placeTestData.addHotelParam());
        RoomResult roomResult = placeService.registerRoomOfHotel(placeTestData.addRoomParam(hotelResult.id()));

        // When
        RoomSimpleResult roomSimpleInfo = placeAccessService.findRoomSimpleInfo(roomResult.id());

        // Then
        assertThat(roomSimpleInfo.roomNumber()).isEqualTo(roomResult.roomNumber());
        assertThat(roomSimpleInfo.roomType()).isEqualTo(roomResult.roomType());
    }

    @Test
    @DisplayName("리뷰 추가 시 평균 별점 테이블이 갱신된다.")
    void addRatingScoreOfHotel_Success() {
        // Given
        double expect = 4.5;
        HotelResult hotelResult = placeService.registerHotel(placeTestData.addHotelParam());
        RoomResult roomResult = placeService.registerRoomOfHotel(placeTestData.addRoomParam(hotelResult.id()));

        // When
        placeAccessService.addRatingScoreOfHotel(roomResult.id(), expect);

        // Then
        HotelResult hotel = placeService.findHotel(hotelResult.id());

        assertThat(hotel.ratingScore()).isEqualTo(expect);
    }

    @Test
    @DisplayName("호텔에 대한 모든 객실 아이디를 반환한다.")
    void findRoomIdsOfHotel_Success() {
        // Given
        HotelResult hotelResult = placeService.registerHotel(placeTestData.addHotelParam());
        RoomResult roomResult = placeService.registerRoomOfHotel(placeTestData.addRoomParam(hotelResult.id()));

        // When
        List<Long> roomIdsOfHotel = placeAccessService.findRoomIdsOfHotel(hotelResult.id());

        // Then
        assertThat(roomIdsOfHotel)
                .isNotEmpty()
                .hasSize(1);
        assertThat(roomIdsOfHotel.get(0)).isEqualTo(roomResult.id());
    }

    @Test
    @DisplayName("존재하는 호텔인지 확인한다.")
    void isHotelValid_Success() {
        // Given
        HotelResult hotelResult = placeService.registerHotel(placeTestData.addHotelParam());

        // When
        boolean hotelValid = placeAccessService.isHotelValid(hotelResult.id());

        // Then
        assertThat(hotelValid).isTrue();
    }

}