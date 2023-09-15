package com.babyblackdog.ddogdog.review.domain.vo;

import static com.babyblackdog.ddogdog.global.exception.ErrorCode.EMPTY_REVIEW_EMAIL;
import static com.babyblackdog.ddogdog.global.exception.ErrorCode.INVALID_EMAIL_FORMAT;

import com.babyblackdog.ddogdog.global.exception.ReviewException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;

@Embeddable
public class Email {

    @NotBlank(message = "이메일이 입력되어야 합니다.")
    @Column(name = "email", nullable = false)
    private String value;

    public Email(String value) {
        validateNull(value);
        validateFormat(value);
        this.value = value;
    }

    protected Email() {
    }

    private void validateNull(String value) {
        if (value == null || value.isBlank()) {
            throw new ReviewException(EMPTY_REVIEW_EMAIL);
        }
    }

    private void validateFormat(String value) {
        if (!value.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            throw new ReviewException(INVALID_EMAIL_FORMAT);
        }
    }

    public String getValue() {
        return value;
    }
}
