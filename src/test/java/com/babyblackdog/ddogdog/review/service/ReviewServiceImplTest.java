package com.babyblackdog.ddogdog.review.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

import com.babyblackdog.ddogdog.common.auth.Email;
import com.babyblackdog.ddogdog.review.domain.Review;
import com.babyblackdog.ddogdog.review.domain.vo.Content;
import com.babyblackdog.ddogdog.review.domain.vo.RatingScore;
import com.babyblackdog.ddogdog.review.service.dto.ReviewResult;
import com.babyblackdog.ddogdog.review.service.dto.ReviewResults;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@SpringBootTest
@Transactional
class ReviewServiceImplTest {

    @Autowired
    private ReviewService service;

    @Autowired
    private ReviewStore store;

    List<Review> savedReviews;

    @BeforeEach
    void setup() {
        savedReviews = new ArrayList<>();
        savedReviews.add(store.registerReview(
                new Review(1L, new Content("객실에 모기가 많았습니다."), new RatingScore(2.5), new Email("test@ddog.dog"))));
        savedReviews.add(store.registerReview(
                new Review(2L, new Content("에어컨이 쾌적했습니다."), new RatingScore(4.0), new Email("test@ddog.dog"))));
        savedReviews.add(store.registerReview(
                new Review(3L, new Content("전체적인 시설이 아주 좋았습니다."), new RatingScore(5.0), new Email("test@ddog.dog"))));
    }

    @Test
    @DisplayName("[registerReview] 유효한 리뷰 정보로 생성할 수 있다.")
    void registerReview_SuccessOnValid() {
        // Given & When
        ReviewResult savedReviewResult = service.registerReview(1L, "객실에 모기가 많았습니다.", 2.5, "test@ddog.dog");

        // Then
        assertThat(savedReviewResult).isNotNull();
        assertThat(savedReviewResult.content()).isEqualTo("객실에 모기가 많았습니다.");
        assertThat(savedReviewResult.rating()).isEqualTo(2.5, within(0.0001));
        assertThat(savedReviewResult.email()).isEqualTo("test@ddog.dog");
    }

    @Test
    @DisplayName("[updateReview] 생성된 리뷰를 수정할 수 있다.")
    void updateReview_SuccessOnValid() {
        // Given
        Long savedReviewId = savedReviews.get(0).getId();

        // When
        ReviewResult savedReviewResult = service.updateReview(savedReviewId, "사장님의 대처가 좋았습니다.", 4.5);

        // Then
        assertThat(savedReviewResult).isNotNull();
        assertThat(savedReviewResult.content()).isEqualTo("사장님의 대처가 좋았습니다.");
        assertThat(savedReviewResult.rating()).isEqualTo(4.5, within(0.0001));
    }

    @Test
    @DisplayName("[findReviewByRoomIds] RoomIds를 이용해 리뷰를 가져올 수 있다.")
    void findReviewByEmail_SuccessOnValid() {
        // Given
        List<Long> roomIds = new ArrayList<>();
        roomIds.add(savedReviews.get(0).getRoomId());
        roomIds.add(savedReviews.get(1).getRoomId());
        roomIds.add(savedReviews.get(2).getRoomId());

        Pageable pageable = PageRequest.of(0, 2);

        // When
        ReviewResults retrievedReviewResults = service.findReviewsByRoomIds(roomIds, pageable);

        // Then
        assertThat(retrievedReviewResults).isNotNull();
        assertThat(retrievedReviewResults.reviewResults().getSize()).isEqualTo(2);
    }

    @Test
    @DisplayName("[findReviewByUserId] Email을 통해 리뷰를 가져올 수 있다.")
    void findReviewByRoomIds_SuccessOnValid() {
        // Given
        Pageable pageable = PageRequest.of(0, 1);

        // When
        ReviewResults retrievedReviewResults = service.findReviewsByEmail("test@ddog.dog", pageable);

        // Then
        assertThat(retrievedReviewResults).isNotNull();
        assertThat(retrievedReviewResults.reviewResults().getSize()).isEqualTo(1);
    }


}