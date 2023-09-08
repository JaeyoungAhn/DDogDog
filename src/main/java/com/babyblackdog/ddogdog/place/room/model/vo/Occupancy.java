package com.babyblackdog.ddogdog.place.room.model.vo;

import static com.babyblackdog.ddogdog.global.exception.ErrorCode.INVALID_OCCUPANCY_LOWER;
import static com.babyblackdog.ddogdog.global.exception.ErrorCode.INVALID_OCCUPANCY_UPPER;

import com.babyblackdog.ddogdog.global.exception.RoomException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.util.Objects;

@Embeddable
public class Occupancy {

  @Min(value = 1, message = "최대 수용 인원은 양수여야 합니다.")
  @Max(value = 10, message = "최대 수용 인원은 10명까지 입니다.")
  @Column(name = "max_occupancy", nullable = false)
  private int maxOccupancy;

  public Occupancy(int maxOccupancy) {
    validate(maxOccupancy);
    this.maxOccupancy = maxOccupancy;
  }

  protected Occupancy() {
  }

  private void validate(int maxOccupancy) {
    validateLower(maxOccupancy);
    validateUpper(maxOccupancy);
  }

  private void validateLower(int maxOccupancy) {
    if (maxOccupancy < 1) {
      throw new RoomException(INVALID_OCCUPANCY_LOWER);
    }
  }

  private void validateUpper(int maxOccupancy) {
    if (maxOccupancy > 10) {
      throw new RoomException(INVALID_OCCUPANCY_UPPER);
    }
  }

  public int getMaxOccupancy() {
    return maxOccupancy;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Occupancy occupancy = (Occupancy) o;
    return maxOccupancy == occupancy.maxOccupancy;
  }

  @Override
  public int hashCode() {
    return Objects.hash(maxOccupancy);
  }
}
