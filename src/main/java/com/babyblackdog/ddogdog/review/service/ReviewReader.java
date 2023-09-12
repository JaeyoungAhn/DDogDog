package com.babyblackdog.ddogdog.review.service;

import com.babyblackdog.ddogdog.review.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewReader {

    Page<Review> findReviewsByReviewIds(List<Long> roomIds, Pageable pageable);

    Review findReviewById(Long reviewId);

    Page<Review> findReviewsByUserId(Long userId, Pageable pageable);

}