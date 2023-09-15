package com.babyblackdog.ddogdog.review.service;

import com.babyblackdog.ddogdog.review.domain.Review;

public interface ReviewStore {

  Review registerReview(Review review);
}