package com.babyblackdog.ddogdog.place.hotel.repository;

import com.babyblackdog.ddogdog.place.hotel.model.Place;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlaceRepository extends JpaRepository<Place, Long> {

  @Query("select p from Place p where p.address.value like %:address%")
  Page<Place> findContainsAddress(@Param("address") String address, Pageable pageable);

}
