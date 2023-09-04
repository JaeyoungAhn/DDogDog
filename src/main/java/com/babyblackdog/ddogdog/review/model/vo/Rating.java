package com.babyblackdog.ddogdog.review.model.vo;

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
    validate(value);
    this.value = value;
  }

  protected Rating() {
  }

  private void validate(Double value) {
    if (value == null) {
      throw new ReviewException(INVALID_RATING);
    }
  }

  public Double getValue() {
    return value;
  }
}
