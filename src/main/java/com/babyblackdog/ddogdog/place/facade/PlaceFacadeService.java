package com.babyblackdog.ddogdog.place.facade;

import static com.babyblackdog.ddogdog.global.exception.ErrorCode.HOTEL_NOT_FOUND;

import com.babyblackdog.ddogdog.global.exception.HotelException;
import com.babyblackdog.ddogdog.place.hotel.model.Hotel;
import com.babyblackdog.ddogdog.place.hotel.repository.HotelRepository;
import com.babyblackdog.ddogdog.place.facade.dto.AddHotelParam;
import com.babyblackdog.ddogdog.place.hotel.service.dto.HotelResult;
import com.babyblackdog.ddogdog.place.room.model.Room;
import com.babyblackdog.ddogdog.place.room.repository.RoomRepository;
import com.babyblackdog.ddogdog.place.facade.dto.AddRoomParam;
import com.babyblackdog.ddogdog.place.room.service.dto.RoomResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PlaceFacadeService {

  private final HotelRepository hotelRepository;
  private final RoomRepository roomRepository;

  public PlaceFacadeService(HotelRepository hotelRepository, RoomRepository roomRepository) {
    this.hotelRepository = hotelRepository;
    this.roomRepository = roomRepository;
  }
  
  public HotelResult registerHotel(AddHotelParam param) {
    Hotel hotel = hotelRepository.save(AddHotelParam.to(param));
    return HotelResult.of(hotel);
  }

  public void deleteHotel(Long hotelId) {
    hotelRepository.deleteById(hotelId);
    roomRepository.deleteByHotelId(hotelId);
  }

  public RoomResult registerRoomOfHotel(AddRoomParam addRoomParam) {
    Hotel hotel = this.findHotelId(addRoomParam.hotelId());
    Room room = AddRoomParam.to(hotel, addRoomParam);
    return RoomResult.of(roomRepository.save(room));
  }

  private Hotel findHotelId(Long hotelId) {
    return hotelRepository.findById(hotelId)
        .orElseThrow(() -> new HotelException(HOTEL_NOT_FOUND));
  }
}
