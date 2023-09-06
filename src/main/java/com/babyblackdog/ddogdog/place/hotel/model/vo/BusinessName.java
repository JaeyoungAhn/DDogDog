package com.babyblackdog.ddogdog.place.hotel.model.vo;

import static com.babyblackdog.ddogdog.place.exception.ErrorCode.INVALID_BUSINESS_NAME;

import com.babyblackdog.ddogdog.place.exception.PlaceException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;

@Embeddable
public class BusinessName {

  @NotBlank(message = "사업장 이름은 반드시 주어져야 합니다.")
  @Column(name = "business_name", nullable = false)
  private String value;

  public BusinessName(String value) {
    validate(value);
    this.value = value;
  }

  protected BusinessName() {
  }

  private void validate(String value) {
    if (value == null || value.isBlank()) {
      throw new PlaceException(INVALID_BUSINESS_NAME);
    }
  }

  public String getValue() {
    return value;
  }
}
