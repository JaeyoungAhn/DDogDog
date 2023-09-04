package com.babyblackdog.ddogdog.global.error;

import java.util.StringJoiner;
import org.springframework.http.HttpStatus;

public enum ErrorCode {
  PLACE_NOT_FOUND(HttpStatus.NOT_FOUND, "P100", "존재하지 않는 숙소입니다."),
  INVALID_BUSINESS_NAME(HttpStatus.BAD_REQUEST, "P200", "사업장 이름은 반드시 주어져야 합니다."),
  INVALID_HUMAN_NAME(HttpStatus.BAD_REQUEST, "P210", "사람 이름은 반드시 주어져야 합니다."),
  INVALID_PHONE_NUMBER_BLANK(HttpStatus.BAD_REQUEST, "P220", "전화번호는 반드시 주어져야 합니다."),
  INVALID_PHONE_NUMBER_LENGTH(HttpStatus.BAD_REQUEST, "P221", "전화번호는 11 자리여야 합니다."),
  INVALID_PHONE_NUMBER_DIGIT(HttpStatus.BAD_REQUEST, "P222", "전화번호는 모두 숫자여야 합니다."),
  INVALID_PLACE_NAME(HttpStatus.BAD_REQUEST, "P230", "숙소 이름은 반드시 주어져야 합니다."),
  INVALID_PROVINCE_VALUE(HttpStatus.BAD_REQUEST, "P240", "지역 이름은 반드시 주어져야 합니다.");

  private final HttpStatus httpStatus;
  private final String code;
  private final String message;

  ErrorCode(HttpStatus httpStatus, String code, String message) {
    this.httpStatus = httpStatus;
    this.code = code;
    this.message = message;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", ErrorCode.class.getSimpleName() + "[", "]")
        .add("httpStatus=" + httpStatus)
        .add("code='" + code + "'")
        .add("message='" + message + "'")
        .toString();
  }
}
