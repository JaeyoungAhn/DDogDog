package com.babyblackdog.ddogdog.common;

import jakarta.persistence.Embeddable;

@Embeddable
public class Point {

    private long value;

    protected Point() {
    }

    public Point(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }
}
