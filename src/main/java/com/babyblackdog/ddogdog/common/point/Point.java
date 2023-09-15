package com.babyblackdog.ddogdog.common.point;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Positive;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Point point = (Point) o;
        return getValue() == point.getValue();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
