package com.babyblackdog.ddogdog.review.repository;

import com.babyblackdog.ddogdog.review.domain.Review;
import com.babyblackdog.ddogdog.review.service.ReviewStore;
import org.springframework.stereotype.Component;

@Component
public class ReviewStoreImpl implements ReviewStore {

    private final ReviewRepository repository;

    public ReviewStoreImpl(ReviewRepository repository) {
        this.repository = repository;
    }

    @Override
    public Review registerReview(Review review) {
        return repository.save(review);
    }
}
