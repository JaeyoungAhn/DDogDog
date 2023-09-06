package com.babyblackdog.ddogdog.place.hotel.model.vo;

import static com.babyblackdog.ddogdog.global.exception.ErrorCode.INVALID_PHONE_BLANK;
import static com.babyblackdog.ddogdog.global.exception.ErrorCode.INVALID_PHONE_DIGIT;
import static com.babyblackdog.ddogdog.global.exception.ErrorCode.INVALID_PHONE_LENGTH;

import com.babyblackdog.ddogdog.global.exception.HotelException;
import io.micrometer.common.util.StringUtils;
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

  protected PhoneNumber() {
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
      throw new HotelException(INVALID_PHONE_DIGIT);
    }
  }

  private void validateLength(String value) {
    if (value.length() != 11) {
      throw new HotelException(INVALID_PHONE_LENGTH);
    }
  }

  private void validateBlank(String value) {
    if (StringUtils.isBlank(value)) {
      throw new HotelException(INVALID_PHONE_BLANK);
    }
  }

  public String getValue() {
    return value;
  }
}
