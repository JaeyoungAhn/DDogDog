package com.babyblackdog.ddogdog.review.repository;

import com.babyblackdog.ddogdog.review.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

public interface ReviewReader {
    Page<Review> findByRoomId(Long roomId, Pageable pageable);
}