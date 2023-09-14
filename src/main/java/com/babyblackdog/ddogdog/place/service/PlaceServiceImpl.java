package com.babyblackdog.ddogdog.place.service;

import static com.babyblackdog.ddogdog.global.exception.ErrorCode.HOTEL_NOT_FOUND;
import static com.babyblackdog.ddogdog.global.exception.ErrorCode.ROOM_NOT_FOUND;

import com.babyblackdog.ddogdog.common.date.StayPeriod;
import com.babyblackdog.ddogdog.global.exception.HotelException;
import com.babyblackdog.ddogdog.global.exception.RoomException;
import com.babyblackdog.ddogdog.global.jwt.JwtSimpleAuthentication;
import com.babyblackdog.ddogdog.mapping.MappingService;
import com.babyblackdog.ddogdog.place.model.Hotel;
import com.babyblackdog.ddogdog.place.model.Room;
import com.babyblackdog.ddogdog.place.model.vo.Province;
import com.babyblackdog.ddogdog.place.repository.HotelRepository;
import com.babyblackdog.ddogdog.place.repository.RoomRepository;
import com.babyblackdog.ddogdog.place.service.dto.AddHotelParam;
import com.babyblackdog.ddogdog.place.service.dto.AddRoomParam;
import com.babyblackdog.ddogdog.place.service.dto.HotelResult;
import com.babyblackdog.ddogdog.place.service.dto.RoomResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PlaceServiceImpl implements
    PlaceService {

  private final HotelRepository hotelRepository;
  private final RoomRepository roomRepository;
  private final MappingService mappingService;

  public PlaceServiceImpl(HotelRepository hotelRepository, RoomRepository roomRepository,
      MappingService mappingService) {
    this.hotelRepository = hotelRepository;
    this.roomRepository = roomRepository;
    this.mappingService = mappingService;
  }

  @Override
  public HotelResult registerHotel(AddHotelParam param) {

    Hotel hotel = hotelRepository.save(AddHotelParam.to(param));
    return HotelResult.of(hotel);
  }

  @Override
  public void deleteHotel(Long hotelId) {
    hotelRepository.deleteById(hotelId);
    roomRepository.deleteByHotelId(hotelId);
  }

  @Override
  public RoomResult registerRoomOfHotel(AddRoomParam addRoomParam) {
    Hotel hotel = hotelRepository.findById(addRoomParam.hotelId())
        .orElseThrow(() -> new RoomException(HOTEL_NOT_FOUND));
    Room room = AddRoomParam.to(hotel, addRoomParam);
    return RoomResult.of(roomRepository.save(room));
  }

  @Override
  public void deleteRoom(Long roomId) {
    roomRepository.deleteById(roomId);
  }

  @Override
  public Page<HotelResult> findHotelsInProvince(Province province, Pageable pageable) {
    Page<Hotel> hotels = hotelRepository.findContainsAddress("서울", pageable);
    return hotels.map(HotelResult::of);
  }

  @Override
  public HotelResult findHotel(Long id) {
    Hotel hotel = hotelRepository.findById(id)
        .orElseThrow(() -> new HotelException(HOTEL_NOT_FOUND));
    return HotelResult.of(hotel);
  }

  @Override
  public RoomResult findRoom(Long roomId) {
    Room room = roomRepository.findById(roomId)
        .orElseThrow(() -> new RoomException(ROOM_NOT_FOUND));
    return RoomResult.of(room);
  }

  @Override
  public RoomResult findRoomForDuration(Long roomId, StayPeriod stayPeriod) {
    Room room = roomRepository.findById(roomId)
        .orElseThrow(() -> new RoomException(ROOM_NOT_FOUND));
    boolean reservationAvailable = mappingService.isReservationAvailableForRoom(
        roomId,
        stayPeriod.checkIn(),
        stayPeriod.checkOut());
    return RoomResult.of(room, reservationAvailable);
  }

  @Override
  public Page<RoomResult> findAllRoomsOfHotelForDuration(Long hotelId, StayPeriod stayPeriod,
      Pageable pageable) {
    Page<Room> rooms = roomRepository.findRoomsByHotelId(hotelId, pageable);
    return rooms.map(room -> {
      boolean reservationAvailable = mappingService.isReservationAvailableForRoom(
          room.getId(),
          stayPeriod.checkIn(),
          stayPeriod.checkOut());
      return RoomResult.of(room, reservationAvailable);
    });
  }
}
