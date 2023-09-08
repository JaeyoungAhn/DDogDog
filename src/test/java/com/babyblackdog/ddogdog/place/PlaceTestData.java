package com.babyblackdog.ddogdog.place;

import com.babyblackdog.ddogdog.common.point.Point;
import com.babyblackdog.ddogdog.place.controller.dto.request.AddHotelRequest;
import com.babyblackdog.ddogdog.place.hotel.model.Hotel;
import com.babyblackdog.ddogdog.place.hotel.model.vo.BusinessName;
import com.babyblackdog.ddogdog.place.hotel.model.vo.HotelName;
import com.babyblackdog.ddogdog.place.hotel.model.vo.HumanName;
import com.babyblackdog.ddogdog.place.hotel.model.vo.PhoneNumber;
import com.babyblackdog.ddogdog.place.hotel.model.vo.Province;
import com.babyblackdog.ddogdog.place.hotel.service.dto.AddHotelParam;
import com.babyblackdog.ddogdog.place.room.model.Room;
import com.babyblackdog.ddogdog.place.room.model.vo.Occupancy;
import com.babyblackdog.ddogdog.place.room.model.vo.RoomNumber;
import com.babyblackdog.ddogdog.place.room.model.vo.RoomType;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class PlaceTestData {

  private final Hotel hotelEntity;
  private final AddHotelRequest addHotelRequest;
  private final AddHotelParam addHotelParam;
  private List<Room> roomEntities;

  public PlaceTestData() {
    this.hotelEntity = instanceofHotel();
    this.addHotelRequest = instanceofHotelRequest();
    this.addHotelParam = AddHotelRequest.to(addHotelRequest);
    this.roomEntities = instanceofRooms();
  }

  private AddHotelRequest instanceofHotelRequest() {
    return new AddHotelRequest(
        "신라호텔",
        "서울",
        1L,
        "010-1234-4321",
        "이부진",
        "신세계"
    );
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

  public Hotel getHotelEntity() {
    return hotelEntity;
  }

  public List<Room> getRoomEntities() {
    return roomEntities;
  }

  public AddHotelRequest getAddHotelRequest() {
    return addHotelRequest;
  }

  public AddHotelParam getAddHotelParam() {
    return addHotelParam;
  }

  public List<Room> bindHotelToRooms(Hotel hotel) {
    this.roomEntities = roomEntities.stream()
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
    return this.roomEntities;
  }
}
