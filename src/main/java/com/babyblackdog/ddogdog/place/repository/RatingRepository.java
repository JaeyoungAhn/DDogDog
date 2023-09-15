package com.babyblackdog.ddogdog.place.repository;

import com.babyblackdog.ddogdog.place.model.Rating;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    Optional<Rating> findByHotelId(Long hotelId);

}
