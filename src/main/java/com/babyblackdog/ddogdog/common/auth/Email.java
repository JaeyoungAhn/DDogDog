package com.babyblackdog.ddogdog.common.auth;

import static com.babyblackdog.ddogdog.global.exception.ErrorCode.EMPTY_EMAIL;
import static com.babyblackdog.ddogdog.global.exception.ErrorCode.INVALID_EMAIL_FORMAT;

import com.babyblackdog.ddogdog.global.exception.ReviewException;
import com.babyblackdog.ddogdog.global.exception.UserException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import java.util.Objects;

@Embeddable
public class Email {

    private static final String REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

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
            throw new UserException(EMPTY_EMAIL);
        }
    }

    private void validateFormat(String value) {
        if (!value.matches(REGEX)) {
            throw new UserException(INVALID_EMAIL_FORMAT);
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
        Email email = (Email) o;
        return Objects.equals(value, email.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
