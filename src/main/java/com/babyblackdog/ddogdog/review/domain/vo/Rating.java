package com.babyblackdog.ddogdog.review.domain.vo;

import com.babyblackdog.ddogdog.global.exception.ReviewException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;

import static com.babyblackdog.ddogdog.global.error.ReviewErrorCode.*;

@Embeddable
public class Rating {

    @NotBlank(message = "별점을 입력해야 합니다.")
    @Column(name = "rating", nullable = false)
    private Double value;

    public Rating(Double value) {
        validateNotNull(value);
        validateDecimalPoint(value);
        validateRatingRange(value);
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

    private void validateRatingRange(Double value) {
        if (value <= -0.1 || value >= 5.1) {
            throw new ReviewException(INVALID_RATING_RANGE);
        }
    }

    public Double getValue() {
        return value;
    }
}
