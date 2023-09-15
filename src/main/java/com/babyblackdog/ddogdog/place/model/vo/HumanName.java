package com.babyblackdog.ddogdog.place.model.vo;

import static com.babyblackdog.ddogdog.global.exception.ErrorCode.INVALID_HUMAN_NAME;

import com.babyblackdog.ddogdog.global.exception.HotelException;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import java.util.Objects;

@Embeddable
public class HumanName {

    @NotBlank(message = "사람 이름은 반드시 주어져야 합니다.")
    @Column(name = "representative", nullable = false)
    private String value;

    public HumanName(String value) {
        validate(value);
        this.value = value;
    }

    protected HumanName() {
    }

    private void validate(String value) {
        if (StringUtils.isBlank(value)) {
            throw new HotelException(INVALID_HUMAN_NAME);
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
        HumanName humanName = (HumanName) o;
        return Objects.equals(value, humanName.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
