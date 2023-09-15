package com.babyblackdog.ddogdog.place.repository;

import com.babyblackdog.ddogdog.place.model.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

    @Query("select p from Hotel p where p.address.value like %:address%")
    Page<Hotel> findContainsAddress(@Param("address") String address, Pageable pageable);

}
