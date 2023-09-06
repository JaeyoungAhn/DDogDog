package com.babyblackdog.ddogdog.common;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Positive;

@Embeddable
public class Point {

  @Positive
  private long value;

  public Point(long value) {
    validate(value);
    this.value = value;
  }

  protected Point() {
  }

  private void validate(long value) {
    if (value < 0) {
      throw new IllegalArgumentException("포인트 값은 양수여야 합니다.");
    }
  }

  public long getValue() {
    return value;
  }
}
