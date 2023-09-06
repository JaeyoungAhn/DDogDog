package com.babyblackdog.ddogdog.place.hotel.model.vo;

import static com.babyblackdog.ddogdog.place.exception.ErrorCode.INVALID_HUMAN_NAME;

import com.babyblackdog.ddogdog.place.exception.PlaceException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;

@Embeddable
public class HumanName {

  @NotBlank(message = "사람 이름은 반드시 주어져야 합니다.")
  @Column(name = "representative", nullable = false)
  private String value;

  public HumanName(String value) {
    validate(value);
    this.value = value;
  }

  private void validate(String value) {
    if (value == null || value.isBlank()) {
      throw new PlaceException(INVALID_HUMAN_NAME);
    }
  }

  protected HumanName() {
  }

  public String getValue() {
    return value;
  }
}
