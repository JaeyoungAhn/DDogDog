package com.babyblackdog.ddogdog.place.hotel.model.vo;

import static com.babyblackdog.ddogdog.global.exception.ErrorCode.INVALID_PROVINCE_VALUE;

import com.babyblackdog.ddogdog.global.exception.HotelException;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import java.util.Objects;

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
    if (StringUtils.isBlank(value)) {
      throw new HotelException(INVALID_PROVINCE_VALUE);
    }
  }

  public String getValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Province province = (Province) o;
    return Objects.equals(value, province.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}
