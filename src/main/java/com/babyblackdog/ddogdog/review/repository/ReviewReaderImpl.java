package com.babyblackdog.ddogdog.review.repository;

import static com.babyblackdog.ddogdog.global.exception.ErrorCode.REVIEW_NOT_FOUND;

import com.babyblackdog.ddogdog.common.auth.Email;
import com.babyblackdog.ddogdog.global.exception.ReviewException;
import com.babyblackdog.ddogdog.review.domain.Review;
import com.babyblackdog.ddogdog.review.service.ReviewReader;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class ReviewReaderImpl implements ReviewReader {

    private final ReviewRepository repository;

    public ReviewReaderImpl(ReviewRepository repository) {
        this.repository = repository;
    }

    @Override
    public Review findReviewById(Long reviewId) {
        return repository.findById(reviewId)
                .orElseThrow(() -> new ReviewException(REVIEW_NOT_FOUND));
    }

    @Override
    public Page<Review> findReviewsByEmail(Email email, Pageable pageable) {
        return repository.findReviewsByEmail(email, pageable);
    }

    @Override
    public Page<Review> findReviewsByRoomIds(List<Long> roomIds, Pageable pageable) {
        return repository.findReviewsByIdIn(roomIds, pageable);
    }
}
