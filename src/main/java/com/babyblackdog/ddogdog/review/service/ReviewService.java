package com.babyblackdog.ddogdog.review.service;

import com.babyblackdog.ddogdog.review.service.dto.ReviewResult;
import com.babyblackdog.ddogdog.review.service.dto.ReviewResults;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface ReviewService {

    /**
     * roomId, content, rating, userId 을 받아 리뷰 생성
     *
     * @param roomId, content, rating, userId
     * @return ReviewResult
     */
    ReviewResult registerReview(Long roomId, String content, Double rating, String email);

    /**
     * reviewId, content 을 받아 리뷰 수정
     *
     * @param reviewId, content
     * @return ReviewResult
     */
    ReviewResult updateReview(Long reviewId, String content);

    /**
     * roomIds, pageable 들을 통해 ReviewResults 를 반환
     *
     * @param roomIds, pageable
     * @return ReviewResults
     */
    ReviewResults findReviewsByRoomIds(List<Long> roomIds, Pageable pageable);

    /**
     * email, pageable 을 통해 Review 들을 반환
     *
     * @param email, pageable
     * @return ReviewResults
     */
    ReviewResults findReviewsByEmail(String email, Pageable pageable);
}
