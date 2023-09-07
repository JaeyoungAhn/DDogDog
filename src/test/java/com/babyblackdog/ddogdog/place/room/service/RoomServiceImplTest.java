package com.babyblackdog.ddogdog.place.room.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import com.babyblackdog.ddogdog.common.date.StayPeriod;
import com.babyblackdog.ddogdog.common.date.TodayDateProvider;
import com.babyblackdog.ddogdog.global.exception.ErrorCode;
import com.babyblackdog.ddogdog.global.exception.RoomException;
import com.babyblackdog.ddogdog.mapping.MappingService;
import com.babyblackdog.ddogdog.place.PlaceTestData;
import com.babyblackdog.ddogdog.place.hotel.model.Hotel;
import com.babyblackdog.ddogdog.place.hotel.repository.HotelRepository;
import com.babyblackdog.ddogdog.place.room.model.Room;
import com.babyblackdog.ddogdog.place.room.repository.RoomRepository;
import com.babyblackdog.ddogdog.place.room.service.dto.RoomResult;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
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
class RoomServiceImplTest {

  @Autowired
  private RoomService roomService;

  @Autowired
  private RoomRepository roomRepository;
  @Autowired
  private HotelRepository hotelRepository;
  @MockBean
  private MappingService mappingService;
  @Autowired
  private PlaceTestData placeTestData;

  private Hotel savedHotel;
  private List<Room> rooms;

  @BeforeEach
  void setUp() {
    savedHotel = hotelRepository.save(placeTestData.getHotelEntity());
    rooms = placeTestData.bindHotelToRooms(savedHotel);
  }

  @Test
  @DisplayName("[findRoomById] 존재하는 객실을 객실 아이디로 조회할 수 있다.")
  void findRoomById_Success() {
    // Given
    Room savedRoom = roomRepository.save(rooms.get(0));

    // When
    RoomResult roomResult = roomService.findRoomById(savedRoom.getId());

    // Then
    assertThat(roomResult).isEqualTo(RoomResult.of(savedRoom));
  }

  @Test
  @DisplayName("[findRoomById] 존재하지 않는 객실을 객실 아이디로 조회할 수 없다.")
  void findRoomById_Exception() {
    // Given
    Long invalidId = 1L;

    // When & Then
    assertThatThrownBy(() -> roomService.findRoomById(invalidId))
        .isInstanceOf(RoomException.class)
        .hasMessage(ErrorCode.ROOM_NOT_FOUND.getMessage());
  }

  @Test
  @DisplayName("[findRoomByIdForDuration] 객실을 특정 기간에 맞게 조회한다.")
  void findRoomBIdForDuration_Success() {
    // Given
    boolean expect = true;
    Room savedRoom = roomRepository.save(rooms.get(0));
    given(mappingService.isReservationAvailableForRoom(anyLong(), any(LocalDate.class),
        any(LocalDate.class)))
        .willReturn(expect);

    // When
    RoomResult roomResult = roomService.findRoomByIdForDuration(
        savedRoom.getId(),
        new StayPeriod(LocalDate.now(), LocalDate.now(),
            new TodayDateProvider()));

    // Then
    assertThat(roomResult).isEqualTo(RoomResult.of(savedRoom, expect));
  }

  @Test
  @DisplayName("[findAllRoomsOfHotelForDuration] 호텔 아이디를 이용해 호텔의 모든 객실을 조회한다.")
  void findAllRoomsOfHotelForDuration_Success() {
    // Given
    roomRepository.saveAll(rooms);

    // When
    Page<RoomResult> roomResults = roomService.findAllRoomsOfHotelForDuration(
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