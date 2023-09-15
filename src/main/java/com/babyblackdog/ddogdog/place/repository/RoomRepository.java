package com.babyblackdog.ddogdog.place.repository;

import com.babyblackdog.ddogdog.place.model.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {

    Page<Room> findRoomsByHotelId(Long hotelId, Pageable pageable);

    void deleteByHotelId(Long hotelId);
}
