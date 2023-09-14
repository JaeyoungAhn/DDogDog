package com.babyblackdog.ddogdog.place;

import com.babyblackdog.ddogdog.common.point.Point;
import com.babyblackdog.ddogdog.place.controller.dto.AddHotelRequest;
import com.babyblackdog.ddogdog.place.controller.dto.AddRoomRequest;
import com.babyblackdog.ddogdog.place.model.Hotel;
import com.babyblackdog.ddogdog.place.model.Rating;
import com.babyblackdog.ddogdog.place.model.Room;
import com.babyblackdog.ddogdog.place.model.vo.BusinessName;
import com.babyblackdog.ddogdog.place.model.vo.HotelName;
import com.babyblackdog.ddogdog.place.model.vo.HumanName;
import com.babyblackdog.ddogdog.place.model.vo.Occupancy;
import com.babyblackdog.ddogdog.place.model.vo.PhoneNumber;
import com.babyblackdog.ddogdog.place.model.vo.Province;
import com.babyblackdog.ddogdog.place.model.vo.RoomNumber;
import com.babyblackdog.ddogdog.place.model.vo.RoomType;
import com.babyblackdog.ddogdog.place.service.dto.AddHotelParam;
import com.babyblackdog.ddogdog.place.service.dto.AddRoomParam;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class PlaceTestData {

  private final Hotel hotelEntity;
  private final AddHotelRequest addHotelRequest;
  private final AddHotelParam addHotelParam;

  private List<Room> roomEntities;
  private final AddRoomRequest addRoomRequest;
  private AddRoomParam addRoomParam;

  private Rating ratingEntity;

  public PlaceTestData() {
    this.hotelEntity = instanceofHotel();
    this.addHotelRequest = instanceofHotelRequest();
    this.addHotelParam = instanceofHotelParam();

    this.roomEntities = instanceofRooms();
    this.addRoomRequest = instanceofRoomRequest();

    this.ratingEntity = instanceofRating();
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

  private AddHotelParam instanceofHotelParam() {
    return AddHotelRequest.to(addHotelRequest);
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

  private AddRoomRequest instanceofRoomRequest() {
    return new AddRoomRequest(
        "더블",
        "침대가 두 개지요",
        4,
        true,
        false,
        true,
        "404",
        50_000
    );
  }

  private Rating instanceofRating() {
    return new Rating(0, 0);
  }

  public AddRoomParam bindHotelIdToRoomParam(Long hotelId) {
    return AddRoomRequest.to(hotelId, addRoomRequest);
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

  public Hotel getHotelEntity() {
    return hotelEntity;
  }

  public AddHotelRequest getAddHotelRequest() {
    return addHotelRequest;
  }

  public AddHotelParam getAddHotelParam() {
    return addHotelParam;
  }

  public List<Room> getRoomEntities() {
    return roomEntities;
  }

  public AddRoomRequest getAddRoomRequest() {
    return addRoomRequest;
  }

  public AddRoomParam getAddRoomParam() {
    return addRoomParam;
  }

  public Rating getRatingEntity() {
    return ratingEntity;
  }
}
