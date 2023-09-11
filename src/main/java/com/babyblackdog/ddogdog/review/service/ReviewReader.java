package com.babyblackdog.ddogdog.review.service;

import com.babyblackdog.ddogdog.review.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewReader {

  Page<Review> findReviewsByReviewIds(Page<Long> reviewIds);

  Review findReviewById(Long reviewId);

  Page<Review> findReviewsByUserId(Long userId, Pageable pageable);

}