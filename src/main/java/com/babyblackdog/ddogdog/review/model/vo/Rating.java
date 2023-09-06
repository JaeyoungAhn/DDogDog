package com.babyblackdog.ddogdog.review.model.vo;

import static com.babyblackdog.ddogdog.global.exception.ErrorCode.INVALID_DECIMAL_POINT;
import static com.babyblackdog.ddogdog.global.exception.ErrorCode.INVALID_RATING;

import com.babyblackdog.ddogdog.global.exception.ReviewException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;

@Embeddable
public class Rating {

  @NotBlank(message = "별점을 입력해야 합니다.")
  @Column(name = "rating", nullable = false)
  private Double value;

  public Rating(Double value) {
    validateNotNull(value);
    validateDecimalPoint(value);
    this.value = value;
  }

  protected Rating() {
  }

  private void validateNotNull(Double value) {
    if (value == null) {
      throw new ReviewException(INVALID_RATING);
    }
  }

  private void validateDecimalPoint(Double value) {
    double multipliedValue = value * 10;
    if (multipliedValue != Math.floor(multipliedValue)) {
      throw new ReviewException(INVALID_DECIMAL_POINT);
    }
  }

  public Double getValue() {
    return value;
  }
}
