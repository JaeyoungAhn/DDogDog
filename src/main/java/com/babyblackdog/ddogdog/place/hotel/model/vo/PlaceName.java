package com.babyblackdog.ddogdog.place.hotel.model.vo;

import static com.babyblackdog.ddogdog.place.exception.ErrorCode.INVALID_PLACE_NAME;

import com.babyblackdog.ddogdog.place.exception.PlaceException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;

@Embeddable
public class PlaceName {

  @NotBlank(message = "숙소 이름은 반드시 주어져야 합니다.")
  @Column(name = "name", nullable = false)
  private String value;

  public PlaceName(String value) {
    validate(value);
    this.value = value;
  }

  protected PlaceName() {
  }

  private void validate(String value) {
    if (value == null || value.isBlank()) {
      throw new PlaceException(INVALID_PLACE_NAME);
    }
  }

  public String getValue() {
    return value;
  }
}
