package com.babyblackdog.ddogdog.review.domain;

import com.babyblackdog.ddogdog.global.exception.ReviewException;
import com.babyblackdog.ddogdog.review.domain.vo.Content;
import com.babyblackdog.ddogdog.review.domain.vo.Rating;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.assertj.core.api.AssertionsForClassTypes.within;

class ReviewTest {

  @Test
  @DisplayName("유효한 리뷰 정보로 생성할 수 있다.")
  void createReviewEntity_SuccessOnValid() {
    // Given & When
    Review review = new Review(new Content("객실에 모기가 많았습니다."), new Rating(2.5), 1L);

    // Then
    assertThat(review.getContent()).isEqualTo("객실에 모기가 많았습니다.");
    assertThat(review.getRating()).isEqualTo(2.5, within(0.0001));
    assertThat(review.getUserId()).isEqualTo(1L);
  }

  @Test
  @DisplayName("비어있는 리뷰는 생성할 수 없다.")
  void createReviewEntity_FailOnEmptyReview() {
    // Given & When
    Exception exception = catchException(() -> new Review(new Content(""), new Rating(2.5), 1L));

    // Then
    assertThat(exception).isInstanceOf(ReviewException.class);
  }

  @Test
  @DisplayName("10자 이하인 리뷰는 생성할 수 없다.")
  void createReviewEntity_FailOnInvalidLength() {
    // Given & When
    Exception exception = catchException(() -> new Review(new Content("좋았다."), new Rating(2.5), 1L));

    // Then
    assertThat(exception).isInstanceOf(ReviewException.class);
  }

  @Test
  @DisplayName("5점 초과인 리뷰는 생성할 수 없다.")
  void createReviewEntity_FailOnExceedingMaxRating() {
    // Given & When
    Exception exception = catchException(() -> new Review(new Content("객실에 모기가 많았습니다."), new Rating(5.5), 1L));

    // Then
    assertThat(exception).isInstanceOf(ReviewException.class);
  }

  @Test
  @DisplayName("0점 미만인 리뷰는 생성할 수 없다.")
  void createReviewEntity_FailBelowMinRating() {
    // Given & When
    Exception exception = catchException(() -> new Review(new Content("객실에 모기가 많았습니다."), new Rating(-1.0), 1L));

    // Then
    assertThat(exception).isInstanceOf(ReviewException.class);
  }

  @Test
  @DisplayName("소숫점 2자리 이상의 별점을 가진 리뷰는 생성할 수 없다.")
  void createReviewEntity_FailOnInvalidDecimalPoint() {
    // Given & When
    Exception exception = catchException(() -> new Review(new Content("객실에 모기가 많았습니다."), new Rating(4.17), 1L));

    // Then
    assertThat(exception).isInstanceOf(ReviewException.class);
  }

  @Test
  @DisplayName("별점으로 평가되지 않은 리뷰는 생성할 수 없다.")
  void createReviewEntity_FailOnNullRating() {
    // Given & When
    Exception exception = catchException(() -> new Review(new Content("객실에 모기가 많았습니다."), new Rating(null), 1L));

    // Then
    assertThat(exception).isInstanceOf(ReviewException.class);
  }

}