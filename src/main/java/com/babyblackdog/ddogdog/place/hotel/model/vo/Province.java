package com.babyblackdog.ddogdog.place.hotel.model.vo;

import static com.babyblackdog.ddogdog.global.error.ErrorCode.INVALID_PROVINCE_VALUE;

import com.babyblackdog.ddogdog.global.exception.PlaceException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;

@Embeddable
public class Province {

  @NotBlank(message = "지역 이름은 반드시 주어져야 합니다.")
  @Column(name = "address", nullable = false)
  private String value;

  public Province(String value) {
    validate(value);
    this.value = value;
  }

  protected Province() {
  }

  private void validate(String value) {
    if (value == null || value.isBlank()) {
      throw new PlaceException(INVALID_PROVINCE_VALUE);
    }
  }

  public String getValue() {
    return value;
  }

}
