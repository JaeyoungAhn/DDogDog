package com.babyblackdog.ddogdog.place.accessor;

import static org.assertj.core.api.Assertions.assertThat;

import com.babyblackdog.ddogdog.place.PlaceTestData;
import com.babyblackdog.ddogdog.place.accessor.vo.RoomSimpleResult;
import com.babyblackdog.ddogdog.place.model.Hotel;
import com.babyblackdog.ddogdog.place.model.Room;
import com.babyblackdog.ddogdog.place.repository.HotelRepository;
import com.babyblackdog.ddogdog.place.repository.RoomRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
class PlaceAccessServiceImplTest {

    @Autowired
    private PlaceAccessService placeAccessService;

    @Autowired
    private PlaceTestData placeTestData;
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private RoomRepository roomRepository;

    private Hotel hotel;
    private List<Room> rooms;

    @BeforeEach
    void setUp() {
        hotel = placeTestData.getHotelEntity();
        rooms = placeTestData.getRoomEntities();
    }

    @Test
    @DisplayName("[findRoomSimpleInfo] 객실 아이디로 예약 정보에 표출할 간단한 객실 정보를 조회한다.")
    void findRoomSimpleInfo_Success() {
        // Given
        Hotel savedHotel = hotelRepository.save(hotel);
        Room savedRoom = roomRepository.save(placeTestData.bindHotelToRooms(savedHotel).get(0));

        // When
        RoomSimpleResult roomSimpleInfo = placeAccessService.findRoomSimpleInfo(savedRoom.getId());

        // Then
        assertThat(roomSimpleInfo.roomNumber()).isEqualTo(savedRoom.getRoomNumber());
        assertThat(roomSimpleInfo.roomType()).isEqualTo(savedRoom.getRoomTypeName());
    }

}