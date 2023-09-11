package com.babyblackdog.ddogdog.review.repository;

import com.babyblackdog.ddogdog.review.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findReviewsByRoomId(Long roomId, Pageable pageable);

    Page<Review> findReviewsByUserId(Long userId, Pageable pageable);
}