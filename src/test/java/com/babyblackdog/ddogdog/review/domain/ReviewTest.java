package com.babyblackdog.ddogdog.review.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.assertj.core.api.AssertionsForClassTypes.within;

import com.babyblackdog.ddogdog.common.auth.Email;
import com.babyblackdog.ddogdog.global.exception.ReviewException;
import com.babyblackdog.ddogdog.review.domain.vo.Content;
import com.babyblackdog.ddogdog.review.domain.vo.RatingScore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReviewTest {

    @Test
    @DisplayName("유효한 리뷰 정보로 생성할 수 있다.")
    void createReviewEntity_SuccessOnValid() {
        // Given & When
        Review review = new Review(1L, new Content("객실에 모기가 많았습니다."), new RatingScore(2.5), new Email("test@ddog.dog"));

        // Then
        assertThat(review.getRoomId()).isEqualTo(1L);
        assertThat(review.getContent()).isEqualTo("객실에 모기가 많았습니다.");
        assertThat(review.getRating()).isEqualTo(2.5, within(0.0001));
        assertThat(review.getEmail()).isEqualTo("test@ddog.dog");
    }

    @Test
    @DisplayName("비어있는 리뷰는 생성할 수 없다.")
    void createReviewEntity_FailOnEmptyReview() {
        // Given & When
        Exception exception = catchException(
                () -> new Review(1L, new Content(""), new RatingScore(2.5), new Email("test@ddog.dog")));

        // Then
        assertThat(exception).isInstanceOf(ReviewException.class);
    }

    @Test
    @DisplayName("10자 이하인 리뷰는 생성할 수 없다.")
    void createReviewEntity_FailOnInvalidLength() {
        // Given & When
        Exception exception = catchException(
                () -> new Review(1L, new Content("좋았다."), new RatingScore(2.5), new Email("test@ddog.dog")));

        // Then
        assertThat(exception).isInstanceOf(ReviewException.class);
    }

    @Test
    @DisplayName("5점 초과인 리뷰는 생성할 수 없다.")
    void createReviewEntity_FailOnExceedingMaxRating() {
        // Given & When
        Exception exception = catchException(
                () -> new Review(1L, new Content("객실에 모기가 많았습니다."), new RatingScore(5.5), new Email("test@ddog.dog")));

        // Then
        assertThat(exception).isInstanceOf(ReviewException.class);
    }

    @Test
    @DisplayName("0점 미만인 리뷰는 생성할 수 없다.")
    void createReviewEntity_FailBelowMinRating() {
        // Given & When
        Exception exception = catchException(
                () -> new Review(1L, new Content("객실에 모기가 많았습니다."), new RatingScore(-1.0), new Email("test@ddog.dog")));

        // Then
        assertThat(exception).isInstanceOf(ReviewException.class);
    }

    @Test
    @DisplayName("소숫점 2자리 이상의 별점을 가진 리뷰는 생성할 수 없다.")
    void createReviewEntity_FailOnInvalidDecimalPoint() {
        // Given & When
        Exception exception = catchException(
                () -> new Review(1L, new Content("객실에 모기가 많았습니다."), new RatingScore(4.17), new Email("test@ddog.dog")));

        // Then
        assertThat(exception).isInstanceOf(ReviewException.class);
    }

    @Test
    @DisplayName("별점으로 평가되지 않은 리뷰는 생성할 수 없다.")
    void createReviewEntity_FailOnNullRating() {
        // Given & When
        Exception exception = catchException(
                () -> new Review(1L, new Content("객실에 모기가 많았습니다."), new RatingScore(null), new Email("test@ddog.dog")));

        // Then
        assertThat(exception).isInstanceOf(ReviewException.class);
    }

    @Test
    @DisplayName("이메일 형식에 어긋난 리뷰는 생성할 수 없다.")
    void createReviewEntity_FailOnInvalidEmail() {
        // Given & When
        Exception exception = catchException(
                () -> new Review(1L, new Content("객실에 모기가 많았습니다."), new RatingScore(null),
                        new Email("te@st@dd@og.dog")));

        // Then
        assertThat(exception).isInstanceOf(ReviewException.class);
    }

}