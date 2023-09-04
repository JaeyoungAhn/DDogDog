package com.babyblackdog.ddogdog.review.repository;

import com.babyblackdog.ddogdog.review.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

}