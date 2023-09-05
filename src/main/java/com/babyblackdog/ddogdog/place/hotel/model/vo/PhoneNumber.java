package com.babyblackdog.ddogdog.place.hotel.model.vo;

import static com.babyblackdog.ddogdog.place.exception.ErrorCode.INVALID_PHONE_BLANK;
import static com.babyblackdog.ddogdog.place.exception.ErrorCode.INVALID_PHONE_DIGIT;
import static com.babyblackdog.ddogdog.place.exception.ErrorCode.INVALID_PHONE_LENGTH;

import com.babyblackdog.ddogdog.place.exception.PlaceException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import java.util.stream.IntStream;

@Embeddable
public class PhoneNumber {

  @NotBlank
  @Column(name = "contact", nullable = false, length = 11)
  private String value;

  public PhoneNumber(String value) {
    validate(value);
    this.value = value;
  }

  private void validate(String value) {
    validateBlank(value);
    validateLength(value);
    validateDigit(value);
  }

  private void validateDigit(String value) {
    boolean isAllDigit = IntStream.range(0, value.length())
        .allMatch(i -> Character.isDigit(value.charAt(i)));
    if (!isAllDigit) {
      throw new PlaceException(INVALID_PHONE_DIGIT);
    }
  }

  private void validateLength(String value) {
    if (value.length() != 11) {
      throw new PlaceException(INVALID_PHONE_LENGTH);
    }
  }

  private void validateBlank(String value) {
    if (value == null || value.isBlank()) {
      throw new PlaceException(INVALID_PHONE_BLANK);
    }
  }

  protected PhoneNumber() {
  }

  public String getValue() {
    return value;
  }
}
