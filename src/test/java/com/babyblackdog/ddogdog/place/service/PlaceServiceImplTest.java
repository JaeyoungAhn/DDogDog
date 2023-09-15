package com.babyblackdog.ddogdog.place.service;

import static com.babyblackdog.ddogdog.global.exception.ErrorCode.HOTEL_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import com.babyblackdog.ddogdog.global.exception.ErrorCode;
import com.babyblackdog.ddogdog.global.exception.HotelException;
import com.babyblackdog.ddogdog.global.exception.RoomException;
import com.babyblackdog.ddogdog.place.PlaceTestData;
import com.babyblackdog.ddogdog.place.model.Hotel;
import com.babyblackdog.ddogdog.place.model.Rating;
import com.babyblackdog.ddogdog.place.model.Room;
import com.babyblackdog.ddogdog.place.model.vo.Province;
import com.babyblackdog.ddogdog.place.repository.HotelRepository;
import com.babyblackdog.ddogdog.place.repository.RatingRepository;
import com.babyblackdog.ddogdog.place.repository.RoomRepository;
import com.babyblackdog.ddogdog.place.service.dto.AddHotelParam;
import com.babyblackdog.ddogdog.place.service.dto.AddRoomParam;
import com.babyblackdog.ddogdog.place.service.dto.HotelResult;
import com.babyblackdog.ddogdog.place.service.dto.RoomResult;
import com.babyblackdog.ddogdog.reservation.service.StayPeriod;
import com.babyblackdog.ddogdog.reservation.service.TodayDateProvider;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@SpringBootTest
@Transactional
class PlaceServiceImplTest {

    @Autowired
    private PlaceService placeService;

    @MockBean
    private MappingService mappingService;

    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private PlaceTestData placeTestData;

    private Hotel hotel;
    private Rating rating;
    private List<Room> rooms;

    @BeforeEach
    void setUp() {
        hotel = placeTestData.getHotelEntity();
        rating = placeTestData.getRatingEntity();
        hotel.setRating(rating);
    }

    @AfterEach
    void tearDown() {
        hotelRepository.deleteAll();
        roomRepository.deleteAll();
    }

    @Test
    @DisplayName("유효한 지역 이름으로 숙소를 조회하면 성공한다.")
    void findHotelsInProvince_ReadSuccess() {
        // Given
        HotelResult hotelResult = placeService.registerHotel(placeTestData.getAddHotelParam());

        // When
        Page<HotelResult> result = placeService.findHotelsInProvince(
                new Province(hotelResult.address()),
                PageRequest.of(0, 2));

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
                invalidProvince,
                PageRequest.of(0, 2));

        // Then
        assertThat(result)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("유효한 숙소 아이디를 이용해 숙소를 조회하면 성공한다.")
    void findPlaceById_ReadSuccess() {
        // Given
        Hotel savedHotel = hotelRepository.save(hotel);

        // When
        HotelResult result = placeService.findHotel(savedHotel.getId());

        // Then
        assertThat(result).isEqualTo(HotelResult.of(savedHotel));
    }

    @Test
    @DisplayName("유효하지 않은 숙소 아이디를 이용해 숙소를 조회하면 실패한다.")
    void findPlaceById_ReadException() {
        // Given
        Long invalidId = 1_000L;

        // When & Then
        assertThatThrownBy(() -> placeService.findHotel(invalidId))
                .isInstanceOf(HotelException.class)
                .hasMessage(HOTEL_NOT_FOUND.toString());
    }

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
        AddRoomParam param = placeTestData.bindHotelIdToRoomParam(hotelResult.id());
        placeService.registerRoomOfHotel(param);

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

    @Test
    @DisplayName("객실 제거 요청으로 제거한다.")
    void deleteRoom_DeleteSuccess() {
        // Given
        Hotel hotel = hotelRepository.save(placeTestData.getHotelEntity());
        Room room = placeTestData.bindHotelToRooms(hotel).get(0);
        Room savedRoom = roomRepository.save(room);

        // When
        placeService.deleteRoom(savedRoom.getId());

        // Then
        assertThat(roomRepository.existsById(room.getId())).isFalse();
    }

    @Test
    @DisplayName("존재하는 객실을 객실 아이디로 조회할 수 있다.")
    void findRoomById_Success() {
        // Given
        Hotel savedHotel = hotelRepository.save(hotel);
        rooms = placeTestData.bindHotelToRooms(savedHotel);
        Room savedRoom = roomRepository.save(rooms.get(0));

        // When
        RoomResult roomResult = placeService.findRoom(savedRoom.getId());

        // Then
        assertThat(roomResult).isEqualTo(RoomResult.of(savedRoom));
    }

    @Test
    @DisplayName("존재하지 않는 객실을 객실 아이디로 조회할 수 없다.")
    void findRoomById_Exception() {
        // Given
        Long invalidId = 1L;

        // When & Then
        assertThatThrownBy(() -> placeService.findRoom(invalidId))
                .isInstanceOf(RoomException.class)
                .hasMessage(ErrorCode.ROOM_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("객실을 특정 기간에 맞게 조회한다.")
    void findRoomIdForDuration_Success() {
        // Given
        boolean expect = true;
        Room savedRoom = roomRepository.save(
                placeTestData.bindHotelToRooms(
                                hotelRepository.save(hotel))
                        .get(0));
        given(mappingService.isReservationAvailableForRoom(anyLong(), any(LocalDate.class),
                any(LocalDate.class)))
                .willReturn(expect);

        // When
        RoomResult roomResult = placeService.findRoomForDuration(
                savedRoom.getId(),
                new StayPeriod(LocalDate.now(), LocalDate.now(),
                        new TodayDateProvider()));

        // Then
        assertThat(roomResult).isEqualTo(RoomResult.of(savedRoom, expect));
    }

    @Test
    @DisplayName("호텔 아이디를 이용해 호텔의 모든 객실을 조회한다.")
    void findAllRoomsOfHotelForDuration_Success() {
        // Given
        Hotel savedHotel = hotelRepository.save(hotel);
        rooms = placeTestData.bindHotelToRooms(savedHotel);
        roomRepository.saveAll(rooms);

        // When
        Page<RoomResult> roomResults = placeService.findAllRoomsOfHotelForDuration(
                savedHotel.getId(),
                new StayPeriod(LocalDate.now(), LocalDate.now(), new TodayDateProvider()),
                PageRequest.of(0, 2));

        // Then
        assertThat(roomResults)
                .isNotNull()
                .isNotEmpty();
        assertThat(roomResults.getContent()).hasSize(rooms.size());
    }


}