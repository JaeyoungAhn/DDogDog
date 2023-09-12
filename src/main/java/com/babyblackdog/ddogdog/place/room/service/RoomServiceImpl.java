package com.babyblackdog.ddogdog.place.room.service;

import static com.babyblackdog.ddogdog.global.exception.ErrorCode.ROOM_NOT_FOUND;

import com.babyblackdog.ddogdog.common.date.StayPeriod;
import com.babyblackdog.ddogdog.global.exception.RoomException;
import com.babyblackdog.ddogdog.mapping.MappingService;
import com.babyblackdog.ddogdog.place.room.model.Room;
import com.babyblackdog.ddogdog.place.room.repository.RoomRepository;
import com.babyblackdog.ddogdog.place.room.service.dto.RoomResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
class RoomServiceImpl implements RoomService {

  private final RoomRepository roomRepository;
  private final MappingService mappingService;

  public RoomServiceImpl(RoomRepository roomRepository, MappingService mappingService) {
    this.roomRepository = roomRepository;
    this.mappingService = mappingService;
  }

  @Override
  public RoomResult findRoomById(Long roomId) {
    Room room = roomRepository.findById(roomId)
        .orElseThrow(() -> new RoomException(ROOM_NOT_FOUND));
    return RoomResult.of(room);
  }

  @Override
  public RoomResult findRoomByIdForDuration(Long roomId, StayPeriod stayPeriod) {
    Room room = roomRepository.findById(roomId)
        .orElseThrow(() -> new RoomException(ROOM_NOT_FOUND));
    boolean reservationAvailable = mappingService.isReservationAvailableForRoom(
        roomId,
        stayPeriod.getCheckIn(),
        stayPeriod.getCheckOut());
    return RoomResult.of(room, reservationAvailable);
  }

  @Override
  public Page<RoomResult> findAllRoomsOfHotelForDuration(Long hotelId, StayPeriod stayPeriod,
      Pageable pageable) {
    Page<Room> rooms = roomRepository.findRoomsByHotelId(hotelId, pageable);
    return rooms.map(room -> {
      boolean reservationAvailable = mappingService.isReservationAvailableForRoom(
          room.getId(),
          stayPeriod.getCheckIn(),
          stayPeriod.getCheckOut());
      return RoomResult.of(room, reservationAvailable);
    });
  }
}
