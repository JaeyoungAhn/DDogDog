package com.babyblackdog.ddogdog.place.service;

import com.babyblackdog.ddogdog.place.model.vo.Province;
import com.babyblackdog.ddogdog.place.service.dto.AddHotelParam;
import com.babyblackdog.ddogdog.place.service.dto.AddRoomParam;
import com.babyblackdog.ddogdog.place.service.dto.HotelResult;
import com.babyblackdog.ddogdog.place.service.dto.RoomResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlaceService {

    HotelResult registerHotel(AddHotelParam param);

    void deleteHotel(Long hotelId);

    RoomResult registerRoomOfHotel(AddRoomParam param);

    void deleteRoom(Long roomId);

    Page<HotelResult> findHotelsInProvince(Province province, Pageable pageable);

    HotelResult findHotel(Long hotelId);

    RoomResult findRoom(Long roomId);

    Page<RoomResult> findRoomsOfHotel(Long hotelId, Pageable pageable);

    boolean existsHotel(Long hotelId);

}
