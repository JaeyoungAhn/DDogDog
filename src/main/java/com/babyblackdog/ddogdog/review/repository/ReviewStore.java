package com.babyblackdog.ddogdog.review.repository;

import com.babyblackdog.ddogdog.review.model.Review;

public interface ReviewStore {

  Review registerReview(Review review);
}