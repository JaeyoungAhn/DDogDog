package com.babyblackdog.ddogdog.order.domain;

public enum OrderStatus {
    CANCELED("취소됨"),
    PREPARED("준비됨"),
    COMPLETED("완료됨");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
