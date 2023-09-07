package com.babyblackdog.ddogdog.place;

import com.babyblackdog.ddogdog.common.point.Point;
import com.babyblackdog.ddogdog.place.hotel.model.Hotel;
import com.babyblackdog.ddogdog.place.hotel.model.vo.BusinessName;
import com.babyblackdog.ddogdog.place.hotel.model.vo.HotelName;
import com.babyblackdog.ddogdog.place.hotel.model.vo.HumanName;
import com.babyblackdog.ddogdog.place.hotel.model.vo.PhoneNumber;
import com.babyblackdog.ddogdog.place.hotel.model.vo.Province;
import com.babyblackdog.ddogdog.place.room.model.Room;
import com.babyblackdog.ddogdog.place.room.model.vo.Occupancy;
import com.babyblackdog.ddogdog.place.room.model.vo.RoomNumber;
import com.babyblackdog.ddogdog.place.room.model.vo.RoomType;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class PlaceTestData {

  private final Hotel hotel;
  private List<Room> rooms;

  public PlaceTestData() {
    this.hotel = instanceofHotel();
    this.rooms = instanceofRooms();
  }

  private List<Room> instanceofRooms() {
    return List.of(
        new Room(
            null,
            RoomType.SINGLE,
            "첫 번째 방입니다.",
            new Occupancy(1),
            true,
            false,
            true,
            new RoomNumber("404"),
            new Point(1000)
        ),
        new Room(
            null,
            RoomType.DELUXE,
            "두 번째 방입니다.",
            new Occupancy(10),
            false,
            true,
            false,
            new RoomNumber("402"),
            new Point(4321)
        )
    );
  }

  private Hotel instanceofHotel() {
    return new Hotel(
        new HotelName("신라호텔"),
        new Province("서울"),
        1L,
        new PhoneNumber("010-1234-1234"),
        new HumanName("이부진"),
        new BusinessName("신세계")
    );
  }

  public Hotel getHotel() {
    return hotel;
  }

  public List<Room> getRooms() {
    return rooms;
  }

  public List<Room> bindHotelToRooms(Hotel hotel) {
    this.rooms = rooms.stream()
        .map(room -> new Room(
            hotel,
            room.getRoomType(),
            room.getDescription(),
            new Occupancy(room.getMaxOccupancy()),
            room.isHasBed(),
            room.isHasAmenities(),
            room.isSmokingAvailable(),
            new RoomNumber(room.getRoomNumber()),
            new Point(room.getPoint())
        )).toList();
    return this.rooms;
  }
}
