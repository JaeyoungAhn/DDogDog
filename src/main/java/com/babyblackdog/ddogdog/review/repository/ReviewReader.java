package com.babyblackdog.ddogdog.review.repository;

import com.babyblackdog.ddogdog.review.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewReader {

  Page<Review> findByRoomId(Long roomId, Pageable pageable);
}