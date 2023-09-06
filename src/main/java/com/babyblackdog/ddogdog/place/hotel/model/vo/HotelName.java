package com.babyblackdog.ddogdog.place.hotel.model.vo;

import static com.babyblackdog.ddogdog.global.error.HotelErrorCode.INVALID_HOTEL_NAME;

import com.babyblackdog.ddogdog.global.exception.HotelException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;

@Embeddable
public class HotelName {

  @NotBlank(message = "숙소 이름은 반드시 주어져야 합니다.")
  @Column(name = "name", nullable = false)
  private String value;

  public HotelName(String value) {
    validate(value);
    this.value = value;
  }

  protected HotelName() {
  }

  private void validate(String value) {
    if (value == null || value.isBlank()) {
      throw new HotelException(INVALID_HOTEL_NAME);
    }
  }

  public String getValue() {
    return value;
  }
}
