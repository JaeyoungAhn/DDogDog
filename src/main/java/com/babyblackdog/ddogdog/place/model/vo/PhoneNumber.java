package com.babyblackdog.ddogdog.place.model.vo;

import static com.babyblackdog.ddogdog.global.exception.ErrorCode.INVALID_PHONE_NUMBER;

import com.babyblackdog.ddogdog.global.exception.HotelException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Embeddable
public class PhoneNumber {

    @Transient
    private final String REGEXP = "^(010|011|016|017|018|019)-[0-9]{3,4}-[0-9]{4}$";

    @jakarta.validation.constraints.Pattern(regexp = REGEXP, message = "올바르지 않은 전화번호 형식입니다.")
    @Column(name = "contact", nullable = false)
    private String value;

    public PhoneNumber(String value) {
        validate(value);
        this.value = value;
    }

    protected PhoneNumber() {
    }

    private void validate(String value) {
        Pattern pattern = Pattern.compile(REGEXP);
        Matcher matcher = pattern.matcher(value);
        if (!matcher.matches()) {
            throw new HotelException(INVALID_PHONE_NUMBER);
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
        PhoneNumber that = (PhoneNumber) o;
        return Objects.equals(REGEXP, that.REGEXP) && Objects.equals(value,
                that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(REGEXP, value);
    }
}
