package com.babyblackdog.ddogdog.review.domain.vo;

import static com.babyblackdog.ddogdog.global.exception.ErrorCode.INVALID_CONTENT;

import com.babyblackdog.ddogdog.global.exception.ReviewException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;

import static com.babyblackdog.ddogdog.global.error.ReviewErrorCode.EMPTY_REVIEW_CONTENT;
import static com.babyblackdog.ddogdog.global.error.ReviewErrorCode.INVALID_REVIEW_LENGTH;

@Embeddable
public class Content {

  @NotBlank(message = "리뷰 내용을 입력해야 합니다.")
  @Column(name = "content", nullable = false)
  private String value;

  public Content(String value) {
    validateNull(value);
    validateLength(value);
    this.value = value;
  }

  protected Content() {
  }

  private void validateNull(String value) {
    if (value == null || value.isBlank()) {
      throw new ReviewException(EMPTY_REVIEW_CONTENT);
    }
  }

  private void validateLength(String value) {
    if (value.length() < 10) {
      throw new ReviewException(INVALID_REVIEW_LENGTH);
    }
  }

  public String getValue() {
    return value;
  }
}
