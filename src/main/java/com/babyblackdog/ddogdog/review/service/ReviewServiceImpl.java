package com.babyblackdog.ddogdog.review.service;

import static com.babyblackdog.ddogdog.global.exception.ErrorCode.INVALID_REVIEW_PERMISSION;

import com.babyblackdog.ddogdog.common.auth.Email;
import com.babyblackdog.ddogdog.common.auth.JwtSimpleAuthentication;
import com.babyblackdog.ddogdog.global.exception.ReviewException;
import com.babyblackdog.ddogdog.review.domain.Review;
import com.babyblackdog.ddogdog.review.domain.vo.Content;
import com.babyblackdog.ddogdog.review.domain.vo.RatingScore;
import com.babyblackdog.ddogdog.review.service.dto.ReviewResult;
import com.babyblackdog.ddogdog.review.service.dto.ReviewResults;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewStore store;
    private final ReviewReader reader;
    private final JwtSimpleAuthentication authentication;

    public ReviewServiceImpl(ReviewStore store, ReviewReader reader, JwtSimpleAuthentication authentication) {
        this.store = store;
        this.reader = reader;
        this.authentication = authentication;
    }

    @Transactional
    @Override
    public ReviewResult registerReview(Long orderId, Long roomId, String content, Double rating, String email) {
        Review review = new Review(orderId, roomId, new Content(content), new RatingScore(rating), new Email(email));
        Review savedReview = store.registerReview(review);
        return ReviewResult.of(savedReview);
    }

    private static boolean doesNotMatch(Email retrievedEmail, Email email) {
        return !email.getValue().equals(retrievedEmail.getValue());
    }

    @Transactional
    @Override
    public ReviewResult updateReview(Long reviewId, String content) {
        Review retrievedReview = reader.findReviewById(reviewId);
        Email email = authentication.getEmail();

        if (doesNotMatch(retrievedReview.getEmail(), email)) {
            throw new ReviewException(INVALID_REVIEW_PERMISSION);
        }

        retrievedReview.setContent(new Content(content));
        return ReviewResult.of(retrievedReview);
    }

    @Override
    public ReviewResults findReviewsByRoomIds(List<Long> roomIds, Pageable pageable) {
        Page<Review> retrievedReviews = reader.findReviewsByRoomIds(roomIds, pageable);
        return ReviewResults.of(retrievedReviews);
    }

    @Override
    public ReviewResults findReviewsByEmail(String email, Pageable pageable) {
        Page<Review> retrievedReviews = reader.findReviewsByEmail(new Email(email), pageable);
        return ReviewResults.of(retrievedReviews);
    }
}
