package com.babyblackdog.ddogdog.common.point;

import static com.babyblackdog.ddogdog.global.exception.ErrorCode.INVALID_POINT;

import com.babyblackdog.ddogdog.global.exception.PointException;
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
            throw new PointException(INVALID_POINT);
        }
    }

    public static Point addPoint(Point origin, Point charge) {
        return new Point(origin.getValue() + charge.getValue());
    }

    public static Point subPoint(Point origin, Point charge) {
        return new Point(origin.getValue() - charge.getValue());
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
