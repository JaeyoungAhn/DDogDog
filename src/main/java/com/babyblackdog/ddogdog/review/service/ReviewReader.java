package com.babyblackdog.ddogdog.review.service;

import com.babyblackdog.ddogdog.common.auth.Email;
import com.babyblackdog.ddogdog.review.domain.Review;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewReader {

    Page<Review> findReviewsByRoomIds(List<Long> roomIds, Pageable pageable);

    Review findReviewById(Long reviewId);

    Page<Review> findReviewsByEmail(Email email, Pageable pageable);
}