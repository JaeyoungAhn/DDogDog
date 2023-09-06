package com.babyblackdog.ddogdog.review.model.vo;

import com.babyblackdog.ddogdog.review.exception.ReviewException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;

import static com.babyblackdog.ddogdog.review.error.ReviewErrorCode.INVALID_CONTENT;

@Embeddable
public class Content {

  @NotBlank(message = "리뷰 내용을 입력해야 합니다.")
  @Column(name = "content", nullable = false)
  private String value;

  public Content(String value) {
    validate(value);
    this.value = value;
  }

  protected Content() {
  }

  private void validate(String value) {
    if (value == null || value.isBlank()) {
      throw new ReviewException(INVALID_CONTENT);
    }
  }

  public String getValue() {
    return value;
  }
}
