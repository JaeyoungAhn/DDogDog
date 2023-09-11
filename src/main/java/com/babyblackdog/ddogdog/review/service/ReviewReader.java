package com.babyblackdog.ddogdog.review.service;

import com.babyblackdog.ddogdog.review.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

public interface ReviewReader {

  Page<Review> findReviewsByRoomId(Long roomId, Pageable pageable);

  Review findReviewById(Long reviewId);

  Page<Review> findReviewsByUserId(Long userId, Pageable pageable);
}